//
//  PopularityViewController.swift
//  iOS
//
//  Created by 정지승 on 2021/07/03.
//

import UIKit
import TagListView

class PopularityViewController: UIViewController {
    
    var dataSource: [WorryDataVO] = []
    
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
    
    func loadWorryData() {
        dataSource.removeAll()

        tableView.reloadData()
    }
}

// MARK: - Delegate
// MARK: TableView
extension PopularityViewController: UITableViewDelegate {
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
        print("\(indexPath.row)")
        
        if let worryViewController = storyboard?.instantiateViewController(identifier: "WorryDetailStoryboard") as? WorryDetailViewController {
            worryViewController.data = dataSource[indexPath.row]
            self.navigationController?.pushViewController(worryViewController, animated: true)
        }
        
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
        cell.selectedCountView.text = "조회수 \(dataSource[indexPath.row].viewCount ?? 0)"
        cell.tagListView.removeAllTags()
        cell.tagListView.addTags(dataSource[indexPath.row].tags ?? [])
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
