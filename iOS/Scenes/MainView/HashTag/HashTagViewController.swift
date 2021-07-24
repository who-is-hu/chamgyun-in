//
//  HashTagViewController.swift
//  iOS
//
//  Created by 정지승 on 2021/07/09.
//

import UIKit

class HashTagViewController: UIViewController {
    // MARK: - Properties
    private var totalDataPageNumber: Int = 0
    private var loadedPageNumber: Int = 0
    private let display: Int = 15
    private let refreshControl: UIRefreshControl = UIRefreshControl()
    
    // MARK: - IBOutlet
    @IBOutlet weak var searchView: UISearchBar!
    @IBOutlet weak var hashTableView: UITableView!
    
    var dataSource: [TagVO] = []
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Do any additional setup after loading the view.
        self.searchView.delegate = self
        
        let hashTagTableViewCellNib: UINib = UINib(nibName: String(describing: HashTagTableViewCell.self), bundle: nil)
        hashTableView.register(hashTagTableViewCellNib, forCellReuseIdentifier: "hashTagTableViewCellNib")
        hashTableView.rowHeight = UITableView.automaticDimension
//        hashTableView.estimatedRowHeight = 60
        hashTableView.delegate = self
        hashTableView.dataSource = self
        
        refreshControl.attributedTitle = NSAttributedString(string: "당겨서 새로고침")
        refreshControl.addTarget(self, action: #selector(pullToRefresh), for: .valueChanged)
        hashTableView.refreshControl = refreshControl
        
        loadHashTagData(page: 0)
    }
    
    
    /// Search HashTag From Sercer
    /// - Parameter searchText: search string. if it is nil then search all hashtag
    func loadHashTagData(page: Int, searchText: String? = nil) {
        let reqUrl: String
        
        
        
        if let searchText = searchText {
            
            reqUrl = "\(APIRequest.tagListGetUrl)?size=\(display)&page=\(page)&key=\(searchText)"
        } else {
            reqUrl = "\(APIRequest.tagListGetUrl)?size=\(display)&page=\(page)"
        }
        
        APIRequest().request(url: reqUrl, method: "GET", voType: PageableTagVO.self) { success, data in
            guard success, let data = data as? PageableTagVO else {
                self.loadedPageNumber-=1
                return
            }
            
            self.dataSource.append(contentsOf: data.content)
            self.totalDataPageNumber = data.totalPages
            self.loadedPageNumber = data.pageable.pageNumber
            
            DispatchQueue.main.async {
                self.hashTableView.reloadData()
                if self.refreshControl.isRefreshing {
                    self.refreshControl.endRefreshing()
                }
            }
        }
    }
    
    // MARK: - Objc
    @objc func pullToRefresh(_ sender: UIRefreshControl) {
        self.dataSource.removeAll()
        self.hashTableView.reloadData()
        
        loadHashTagData(page: 0, searchText: self.searchView.text)
    }
}

// MARK: - Extension and Delegate
// MARK: SearchView delegate extension
extension HashTagViewController: UISearchBarDelegate {
    func searchBarSearchButtonClicked(_ searchBar: UISearchBar) {
        if let text = searchBar.text {
            searchHashTag(text: text)
        }
    }
    
    func searchBarCancelButtonClicked(_ searchBar: UISearchBar) {
        self.searchView.text = nil
    }
    
    func searchHashTag(text: String) {
        self.dataSource.removeAll()
        self.hashTableView.reloadData()
        
        loadHashTagData(page: 0, searchText: text)
    }
}

// MARK: TableView delegate extension
extension HashTagViewController: UITableViewDelegate {
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        print("selected row : \(indexPath.row)")
        tableView.deselectRow(at: indexPath, animated: true)
        
        if let hashGroupWorryController = storyboard?.instantiateViewController(identifier: "HashGroupWorryStoryboard") as? HashGroupWorryViewController {
            hashGroupWorryController.hashText = dataSource[indexPath.row].name
            self.navigationController?.pushViewController(hashGroupWorryController, animated: true)
        }
    }
    
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        let offsetY = scrollView.contentOffset.y
        let contentHeight = scrollView.contentSize.height
        
        if offsetY > contentHeight - scrollView.frame.height {
            if totalDataPageNumber-1 > loadedPageNumber {
                loadedPageNumber+=1
                loadHashTagData(page: loadedPageNumber)
            }
        }
    }
}

extension HashTagViewController: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.dataSource.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "hashTagTableViewCellNib") as! HashTagTableViewCell
        
        cell.hashTitleLabel.text = "\(dataSource[indexPath.row].name)"
        cell.hashPostCntLabel.text = "고민 개수 : \(dataSource[indexPath.row].postNumber ?? 0)"
        
        return cell
    }
}
