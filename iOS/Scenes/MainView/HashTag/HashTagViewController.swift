//
//  HashTagViewController.swift
//  iOS
//
//  Created by 정지승 on 2021/07/09.
//

import UIKit

class HashTagViewController: UIViewController {
    
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
        hashTableView.estimatedRowHeight = 57
        hashTableView.delegate = self
        hashTableView.dataSource = self
        
        loadHashTagData()
    }
    
    
    /// Search HashTag From Sercer
    /// - Parameter searchText: search string. if it is nil then search all hashtag
    func loadHashTagData(searchText: String? = nil) {
        // sample
        dataSource.removeAll()
        
        
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
        loadHashTagData()
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
}

extension HashTagViewController: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.dataSource.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "hashTagTableViewCellNib") as! HashTagTableViewCell
        
        cell.hashTitleLabel.text = "\(dataSource[indexPath.row].name)"
        cell.hashPostCntLabel.text = "고민 개수 : \(dataSource[indexPath.row].postNumber)"
        
        return cell
    }
}
