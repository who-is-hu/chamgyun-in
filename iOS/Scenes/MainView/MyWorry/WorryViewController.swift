//
//  WorryViewController.swift
//  iOS
//
//  Created by 정지승 on 2021/07/10.
//

import UIKit
import TagListView

class WorryViewController: UIViewController {
    
    /// Data Load Type. MyWorry type load my worry boards. AnsWorry type load answer worry boards.
    enum LoadType {
        case MyWorry
        case AnsWorry
    }
    
    // MARK: - Properties
    var loadType: LoadType?
    var dataSource: [WorryDataVO] = []
    private var lastLoadedPage: Int = 0
    private var totalPage: Int = 0
    private let display: Int = 10
    
    // MARK: - IBOutlet
    @IBOutlet weak var worryTableView: UITableView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let nibName = String(describing: WorryTableViewCell.self)
        let nib = UINib(nibName: nibName, bundle: nil)
        worryTableView.register(nib, forCellReuseIdentifier: nibName)
        worryTableView.rowHeight = UITableView.automaticDimension
        worryTableView.estimatedRowHeight = 151
        
        worryTableView.delegate = self
        worryTableView.dataSource = self
        
        loadWorryData()
    }
    
    func loadWorryData(page: Int = 0) {
        // load my worry boards
        lastLoadedPage = page
        
        if loadType == .MyWorry {

            APIRequest().request(url: "\(APIRequest.worryPostUrl)/my?page=\(page)&size=\(display)", method: "GET", voType: PageableWorryDataVO.self) { success, data in
                if success {
                    if let data = data as?
                        PageableWorryDataVO {
                        
                        self.totalPage = data.totalPages
                        
                        for worryVO in data.content {
                            self.dataSource.append(worryVO)
                        }
                        
                        DispatchQueue.main.async {
                            self.worryTableView.reloadData()
                        }
                        
                        print(data)
                    }
                }
            }
            
        } else {
            // load answer worry boards

        }
    }
}

// MARK: - Extension and Delegate
extension WorryViewController: UITableViewDelegate {
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
        
        if let worryDetailViewController = storyboard?.instantiateViewController(identifier: "WorryDetailStoryboard") as? WorryDetailViewController {
            worryDetailViewController.data = dataSource[indexPath.row]
            self.navigationController?.pushViewController(worryDetailViewController, animated: true)
        }
    }
}

extension WorryViewController: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return dataSource.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        if totalPage > lastLoadedPage && indexPath.row > dataSource.count - (display - 7) {
            loadWorryData(page: lastLoadedPage+1)
        }
        
        let cell = tableView.dequeueReusableCell(withIdentifier: String(describing: WorryTableViewCell.self), for: indexPath) as! WorryTableViewCell
        
        cell.titleView.text = dataSource[indexPath.row].title
        cell.bodyView.text = dataSource[indexPath.row].body
        cell.selectedCountView.text = "조회수 \(dataSource[indexPath.row].viewCount ?? 0)"
        cell.tagListView.removeAllTags()
        if let tags = dataSource[indexPath.row].splitTags {
            cell.tagListView.addTags(tags)
        }
        cell.tagListView.delegate = self
        cell.tagListView.textFont = UIFont.boldSystemFont(ofSize: 13)
        
        return cell
    }
}

// taglistview
extension WorryViewController: TagListViewDelegate {
    func tagPressed(_ title: String, tagView: TagView, sender: TagListView) {
        print("tab pressed title : \(title)")
    }
}


