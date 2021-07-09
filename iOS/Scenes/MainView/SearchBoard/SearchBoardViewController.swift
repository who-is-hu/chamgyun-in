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
        dataSource.append(WorryDataVO(id: 0,title: "worry1", body: "t has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum co(The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular duomes from a line in section 1.10.32.", viewCount: 3, tags: ["#AA", "#BB", "#CC"], viewType: .N))
        
        dataSource.append(WorryDataVO(id: 1,title: "worry2", body: "t has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum co(The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular duomes from a line in section 1.10.32.", viewCount:5, tags: ["#AA", "#DD", "#CC"], viewType: .OX))
        
        dataSource.append(WorryDataVO(id: 2,title: "worry3", body: "t has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum co(The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular duomes from a line in section 1.10.32.", viewCount: 4, tags: ["#AA", "#BB", "#CC"], viewType: .N))
        
        dataSource.append(WorryDataVO(id: 3,title: "worry4", body: "t has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum co(The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular duomes from a line in section 1.10.32.", viewCount: 3, tags: ["#AA", "#BB", "#CC"], viewType: .N))
        
        dataSource.append(WorryDataVO(id: 4,title: "worry5", body: "t has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum co(The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular duomes from a line in section 1.10.32.", viewCount:5, tags: ["#AA", "#BB", "#CC"], viewType: .N))
        
        dataSource.append(WorryDataVO(id: 5,title: "worry6", body: "t has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum co(The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular duomes from a line in section 1.10.32.", viewCount: 4, tags: ["#AA", "#BB", "#CC"], viewType: .N))
        
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
            worryViewController.data = dataSource[indexPath.row]
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
        cell.selectedCountView.text = "조회수 \(dataSource[indexPath.row].viewCount)"
        cell.tagListView.removeAllTags()
        cell.tagListView.addTags(dataSource[indexPath.row].tags)
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
