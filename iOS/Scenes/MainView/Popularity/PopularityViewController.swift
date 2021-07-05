//
//  PopularityViewController.swift
//  iOS
//
//  Created by 정지승 on 2021/07/03.
//

import UIKit
import TagListView

struct WorryDataVO {
    let title: String
    let body: String
    let viewCount: Int
    let tags: [String]
}

class PopularityViewController: UIViewController {
    
    var dataSource: [WorryDataVO] = []
    
    // MARK: IBOutlet
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
    
    func loadWorryData() {
        dataSource.removeAll()
        dataSource.append(WorryDataVO(title: "worry1", body: "t has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum co(The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular duomes from a line in section 1.10.32.", viewCount: 3, tags: ["#AA", "#BB", "#CC"]))
        
        dataSource.append(WorryDataVO(title: "worry2", body: "t has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum co(The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular duomes from a line in section 1.10.32.", viewCount:5, tags: ["#AA", "#BB", "#CC"]))
        
        dataSource.append(WorryDataVO(title: "worry3", body: "t has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum co(The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular duomes from a line in section 1.10.32.", viewCount: 4, tags: ["#AA", "#BB", "#CC"]))
        
        dataSource.append(WorryDataVO(title: "worry4", body: "t has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum co(The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular duomes from a line in section 1.10.32.", viewCount: 3, tags: ["#AA", "#BB", "#CC"]))
        
        dataSource.append(WorryDataVO(title: "worry5", body: "t has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum co(The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular duomes from a line in section 1.10.32.", viewCount:5, tags: ["#AA", "#BB", "#CC"]))
        
        dataSource.append(WorryDataVO(title: "worry6", body: "t has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum co(The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular duomes from a line in section 1.10.32.", viewCount: 4, tags: ["#AA", "#BB", "#CC"]))
        
        
    }
}

// MARK: - Delegate
// MARK: TableView
extension PopularityViewController: UITableViewDelegate {
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
        print("\(indexPath.row)")
    }
}


extension PopularityViewController: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return dataSource.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "worryViewCell", for: indexPath) as! WorryTableViewCell
        
        cell.titleView.text = dataSource[indexPath.row].title
        cell.bodyView.text = dataSource[indexPath.row].body
        cell.selectedCountView.text = "조회수 \(dataSource[indexPath.row].viewCount)"
        cell.tagListView.removeAllTags()
        cell.tagListView.addTags(dataSource[indexPath.row].tags)
        cell.tagListView.delegate = self
        cell.tagListView.textFont = UIFont.boldSystemFont(ofSize: 13)
        
        return cell
    }
}

extension PopularityViewController: TagListViewDelegate {
    func tagPressed(_ title: String, tagView: TagView, sender: TagListView) {
        print("tab pressed title : \(title)")
    }
}
