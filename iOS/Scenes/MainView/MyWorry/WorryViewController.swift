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
    let refreshControl: UIRefreshControl = UIRefreshControl()
    
    // MARK: - IBOutlet
    @IBOutlet weak var worryTableView: UITableView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let nibName = String(describing: WorryTableViewCell.self)
        let nib = UINib(nibName: nibName, bundle: nil)
        worryTableView.register(nib, forCellReuseIdentifier: nibName)
        worryTableView.rowHeight = UITableView.automaticDimension
        worryTableView.estimatedRowHeight = 151
        
        refreshControl.attributedTitle = NSAttributedString(string: "당겨서 새로고침")
        refreshControl.addTarget(self, action: #selector(pullToRefresh), for: .valueChanged)
        
        worryTableView.refreshControl = refreshControl
        
        worryTableView.delegate = self
        worryTableView.dataSource = self
        
        loadWorryData()
    }
    
    func loadWorryData(page: Int = 0) {
        // load my worry boards
        lastLoadedPage = page
        let url: String
        if loadType == .MyWorry {
            url = "\(APIRequest.worryPostUrl)/my?page=\(page)&size=\(display)"
        } else {
            // load answer worry boards
            url = "\(APIRequest.votePostUrl)/participate-post?page=\(page)&size=\(display)"
        }
        
        APIRequest().request(url: url, method: "GET", voType: PageableWorryDataVO.self) { success, data in
            if success {
                if let data = data as?
                    PageableWorryDataVO {
                    
                    self.totalPage = data.totalPages
                    
                    for worryVO in data.content {
                        self.dataSource.append(worryVO)
                    }
                    
                    DispatchQueue.main.async {
                        self.worryTableView.reloadData()
                        
                        if self.refreshControl.isRefreshing {
                            self.refreshControl.endRefreshing()
                        }
                    }
                    
                    print(data)
                }
            }
        }
    }
    
    // MARK: - objc
    @objc func pullToRefresh(_ sender: UIRefreshControl) {
        self.dataSource.removeAll()
        self.worryTableView.reloadData()
        
        self.loadWorryData()
    }
    
}

// MARK: - Extension and Delegate
extension WorryViewController: UITableViewDelegate {
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
        
        if let worryDetailViewController = storyboard?.instantiateViewController(identifier: "WorryDetailStoryboard") as? WorryDetailViewController {
            worryDetailViewController.postId = dataSource[indexPath.row].id
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
            lastLoadedPage += 1
            loadWorryData(page: lastLoadedPage)
        }
        
        let cell = tableView.dequeueReusableCell(withIdentifier: String(describing: WorryTableViewCell.self), for: indexPath) as! WorryTableViewCell
        
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
        
        if let state = dataSource[indexPath.row].state, state == "CLOSE" {
            cell.endVotedButton.isHidden = false
        }
        
        return cell
    }
}

// taglistview
extension WorryViewController: TagListViewDelegate {
    func tagPressed(_ title: String, tagView: TagView, sender: TagListView) {
        print("tab pressed title : \(title)")
    }
}


