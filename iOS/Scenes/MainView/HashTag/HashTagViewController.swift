//
//  HashTagViewController.swift
//  iOS
//
//  Created by 정지승 on 2021/07/09.
//

import UIKit


struct HashInfoVO {
    let hashtag: String
    let boardCnt: Int
}

class HashTagViewController: UIViewController {
    
    // MARK: - IBOutlet
    @IBOutlet weak var searchView: UISearchBar!
    @IBOutlet weak var hashTableView: UITableView!
    
    var dataSource: [HashInfoVO] = []
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Do any additional setup after loading the view.
        self.searchView.delegate = self
        
        let hashTagTableViewCellNib: UINib = UINib(nibName: String(describing: HashTagTableViewCell.self), bundle: nil)
        hashTableView.register(hashTagTableViewCellNib, forCellReuseIdentifier: "hashTagTableViewCellNib")
        hashTableView.rowHeight = UITableView.automaticDimension
        hashTableView.estimatedRowHeight = 57
        hashTableView.delegate = self
        hashTableView.dataSource = self
        
        loadHashTagData()
    }
    
    
    /// Search HashTag From Sercer
    /// - Parameter searchText: search string. if it is nil then search all hashtag
    func loadHashTagData(searchText: String? = nil) {
        // sample
        dataSource.append(HashInfoVO(hashtag: "감자", boardCnt: 3))
        dataSource.append(HashInfoVO(hashtag: "바나나", boardCnt: 1))
        dataSource.append(HashInfoVO(hashtag: "딸기", boardCnt: 2))
        dataSource.append(HashInfoVO(hashtag: "자전거", boardCnt: 20))
        dataSource.append(HashInfoVO(hashtag: "유튜브", boardCnt: 22))
        dataSource.append(HashInfoVO(hashtag: "자전거", boardCnt: 20))
        dataSource.append(HashInfoVO(hashtag: "유튜브", boardCnt: 22))
        dataSource.append(HashInfoVO(hashtag: "자전거", boardCnt: 20))
        dataSource.append(HashInfoVO(hashtag: "유튜브", boardCnt: 22))
        
        hashTableView.reloadData()
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
        print("search string : \(text)")
        // load data from server and apply to table datasource
        
        // --
        self.dataSource = []
        loadHashTagData()
    }
}

// MARK: TableView delegate extension
extension HashTagViewController: UITableViewDelegate {
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        print("selected row : \(indexPath.row)")
        tableView.deselectRow(at: indexPath, animated: true)
    }
}

extension HashTagViewController: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.dataSource.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "hashTagTableViewCellNib") as! HashTagTableViewCell
        
        cell.hashTitleLabel.text = "\(dataSource[indexPath.row].hashtag)"
        cell.hashPostCntLabel.text = "고민 개수 : \(dataSource[indexPath.row].boardCnt)"
        
        return cell
    }
}
