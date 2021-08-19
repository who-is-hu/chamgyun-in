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
    let refreshControl: UIRefreshControl = UIRefreshControl()
    
    private var totalDataPageNumber: Int = 0
    private var loadedPageNumber: Int = 0
    
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
        
        self.refreshControl.attributedTitle = NSAttributedString(string: "당겨서 새로고침")
        self.refreshControl.addTarget(self, action: #selector(pullToRefresh), for: .valueChanged)
        self.tableView.refreshControl = self.refreshControl
        
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
                
                self.loadedPageNumber = data.pageable.pageNumber
                self.totalDataPageNumber = data.totalPages
                
                self.dataSource.append(contentsOf: data.content)
                
                DispatchQueue.main.async {
                    self.tableView.reloadData()
                    
                    if self.refreshControl.isRefreshing {
                        self.refreshControl.endRefreshing()
                    }
                }
            }
        }
    }
    
    
    // MARK: - objc
    @objc func pullToRefresh(_ sender: UIRefreshControl) {
        self.dataSource.removeAll()
        self.tableView.reloadData()
        
        loadWorryData()
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
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        let offsetY = scrollView.contentOffset.y
        let contentHeight = scrollView.contentSize.height
        
        if offsetY > contentHeight - scrollView.frame.height {
            if totalDataPageNumber - 1 > loadedPageNumber {
                loadedPageNumber += 1
                loadWorryData(page: loadedPageNumber)
            }
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
            cell.ansStateButton.setImage(UIImage(systemName: "checkmark.square.fill"), for: .normal)
            cell.ansStateButton.setTitleColor(UIColor.red, for: .normal)
            cell.ansStateButton.tintColor = UIColor.red
        } else {
            cell.ansStateButton.setImage(UIImage(systemName: "square"), for: .normal)
            cell.ansStateButton.setTitleColor(UIColor.lightGray, for: .normal)
            cell.ansStateButton.tintColor = UIColor.lightGray
        }
        
        if let state = dataSource[indexPath.row].state {
            cell.endVotedButton.isHidden = (state == "IN_PROGRESS")
        }
        
        return cell
    }
}

extension InterestListViewController: TagListViewDelegate {
    func tagPressed(_ title: String, tagView: TagView, sender: TagListView) {
        print("tab pressed title : \(title)")
    }
}
