//
//  SearchBoardViewController.swift
//  iOS
//
//  Created by 정지승 on 2021/07/09.
//

import UIKit
import TagListView

class SearchBoardViewController: UIViewController {
    // MARK: - Properties
    var dataSource: [WorryDataVO] = []
    
    // MARK: - IBOutlet
    @IBOutlet weak var searchView: UISearchBar!
    @IBOutlet weak var boardTableView: UITableView!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        self.searchView.delegate = self
        
        let worryTableViewCellNib: UINib = UINib(nibName: String(describing: WorryTableViewCell.self), bundle: nil)
        
        self.boardTableView.register(worryTableViewCellNib, forCellReuseIdentifier: String(describing: WorryTableViewCell.self))
        self.boardTableView.rowHeight = UITableView.automaticDimension
        self.boardTableView.estimatedRowHeight = 151
        self.boardTableView.delegate = self
        self.boardTableView.dataSource = self
    }

    // MARK: - Function
    
    /// Search HashTag From Sercer
    /// - Parameter searchText: search string. if it is nil then search all worry board
    func loadWorryBoardData(text: String? = nil) {
        dataSource.removeAll()

        
        boardTableView.reloadData()
    }
}

// MARK: extension and delegate
// search view
extension SearchBoardViewController: UISearchBarDelegate {
    func searchBarSearchButtonClicked(_ searchBar: UISearchBar) {
        if let text = searchBar.text {
            print("search data : \(text)")
            loadWorryBoardData(text: text)
        }
    }
}

// table view
extension SearchBoardViewController: UITableViewDelegate {
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        print("selected row : \(indexPath.row)")
        boardTableView.deselectRow(at: indexPath, animated: true)
        
        if let worryViewController = storyboard?.instantiateViewController(identifier: "WorryDetailStoryboard") as? WorryDetailViewController {
            worryViewController.postId = dataSource[indexPath.row].id
            self.navigationController?.pushViewController(worryViewController, animated: true)
        }
    }
}

extension SearchBoardViewController: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.dataSource.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: String(describing: WorryTableViewCell.self)) as! WorryTableViewCell
        let data = dataSource[indexPath.row]
        
        cell.titleView.text = data.title
        cell.bodyView.text = data.body
//        cell.selectedCountView.text = "조회수 \(dataSource[indexPath.row].viewCount ?? 0)"
        cell.tagListView.removeAllTags()
        
        if let tags = dataSource[indexPath.row].tags, let splitTags = tags.split(separator: ",") as? [String] {
            
            cell.tagListView.addTags(splitTags)
        }
        
        if let isVoted = dataSource[indexPath.row].voted, isVoted {
            cell.ansStateButton.setImage(UIImage(systemName: "checkmark.square.fill"), for: .normal)
            cell.ansStateButton.setTitleColor(UIColor.red, for: .normal)
            cell.ansStateButton.tintColor = UIColor.red
        } else {
            cell.ansStateButton.setImage(UIImage(systemName: "square"), for: .normal)
            cell.ansStateButton.setTitleColor(UIColor.lightGray, for: .normal)
            cell.ansStateButton.tintColor = UIColor.lightGray
        }
        
        cell.tagListView.delegate = self
        cell.tagListView.textFont = UIFont.boldSystemFont(ofSize: 13)
        
        return cell
    }
    
    
}

// taglistview
extension SearchBoardViewController: TagListViewDelegate {
    func tagPressed(_ title: String, tagView: TagView, sender: TagListView) {
        print("taped tag : \(title)")
    }
}
