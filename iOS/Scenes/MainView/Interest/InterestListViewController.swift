//
//  PopularityViewController.swift
//  iOS
//
//  Created by 정지승 on 2021/07/03.
//

import UIKit
import TagListView

class InterestListViewController: UIViewController {
    
    // MARK: - Properties
    var dataSource: [WorryDataVO] = []
    let display: Int = 10
    
    // MARK: - IBOutlet
    @IBOutlet weak var tableView: UITableView!
    
    // MARK: - Lifecycle Method
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Do any additional setup after loading the view.
        // Load Table Cell
        let worryTableCellNib: UINib = UINib(nibName: String(describing: WorryTableViewCell.self), bundle: nil)
        
        self.tableView.register(worryTableCellNib, forCellReuseIdentifier: "worryViewCell")
        
        self.tableView.rowHeight = UITableView.automaticDimension
        self.tableView.estimatedRowHeight = 180
        
        self.tableView.delegate = self
        self.tableView.dataSource = self
        
        loadWorryData()
    }
    
    func loadWorryData(page: Int = 0) {
        // load interest tags
        APIRequest().request(url: "\(APIRequest.tagGetPatchUrl)", method: "GET", voType: [TagVO].self) { success, data in
            guard success, let data = data as? [TagVO] else {
                print("failed load my interest tag")
                return
            }
            
            let tags = data.map { $0.name }
            let url: String
            
            guard tags.count > 0 else {
                // plz add interest
                return
            }
            
            url = "\(APIRequest.worryPostUrl)?page=\(page)&size=\(self.display)&tags=\(tags.joined(separator: ","))"
            
            print(url)
            
            APIRequest().request(url: url, method: "GET", voType: PageableWorryDataVO.self) { success, data in
                guard success, let data = data as? PageableWorryDataVO else {
                    print("failed load worry data")
                    return
                }
                
                self.dataSource.removeAll()
                self.dataSource.append(contentsOf: data.content)
                
                DispatchQueue.main.async {
                    self.tableView.reloadData()
                }
            }
        }
    }
}

// MARK: - Delegate
// MARK: TableView
extension InterestListViewController: UITableViewDelegate {
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
        print("\(indexPath.row)")
        
        if let worryViewController = storyboard?.instantiateViewController(identifier: "WorryDetailStoryboard") as? WorryDetailViewController {
            worryViewController.postId = dataSource[indexPath.row].id
            self.navigationController?.pushViewController(worryViewController, animated: true)
        }
        
    }
}


extension InterestListViewController: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return dataSource.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "worryViewCell", for: indexPath) as! WorryTableViewCell
        
        cell.titleView.text = dataSource[indexPath.row].title
        cell.bodyView.text = dataSource[indexPath.row].body
//        cell.selectedCountView.text = "조회수 \(dataSource[indexPath.row].viewCount ?? 0)"
        cell.tagListView.removeAllTags()
        
        if let tags = dataSource[indexPath.row].splitTags {
            cell.tagListView.addTags(tags)
        }
        
        cell.tagListView.delegate = self
        cell.tagListView.textFont = UIFont.boldSystemFont(ofSize: 13)
        
        if let isVoted = dataSource[indexPath.row].voted, isVoted {
            cell.ansStateButton.isHidden = false
        }
        
        return cell
    }
}

extension InterestListViewController: TagListViewDelegate {
    func tagPressed(_ title: String, tagView: TagView, sender: TagListView) {
        print("tab pressed title : \(title)")
    }
}
