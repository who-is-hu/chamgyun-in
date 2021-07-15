//
//  HashGroupWorryViewController.swift
//  iOS
//
//  Created by 정지승 on 2021/07/09.
//

import UIKit
import TagListView

class HashGroupWorryViewController: UIViewController {
    // MARK: - Properties
    var dataSource: [WorryDataVO] = []
    var hashText: String? = nil
    
    // MARK: - IBOutlet
    @IBOutlet weak var tableView: UITableView!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        let nib = UINib(nibName: String(describing: WorryTableViewCell.self), bundle: nil)
        self.tableView.register(nib, forCellReuseIdentifier: String(describing: WorryTableViewCell.self))
        self.tableView.rowHeight = UITableView.automaticDimension
        self.tableView.estimatedRowHeight = 151
        self.tableView.delegate = self
        self.tableView.dataSource = self
    }
    
    override func viewWillAppear(_ animated: Bool) {
        if let hashText = self.hashText {
            self.title = "#\(hashText)"
            loadWorryData(text: hashText)
        }
    }
    
    func loadWorryData(text: String) {
        // load data from server using text
        
        //
        dataSource.removeAll()
        

        
        tableView.reloadData()
    }
}

// MARK: - extension and delegate
// tableview
extension HashGroupWorryViewController: UITableViewDelegate {
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        print("selected row : \(indexPath.row)")
        tableView.deselectRow(at: indexPath, animated: true)
        
        if let worryViewController = storyboard?.instantiateViewController(identifier: "WorryDetailStoryboard") as? WorryDetailViewController {
            worryViewController.data = dataSource[indexPath.row]
            self.navigationController?.pushViewController(worryViewController, animated: true)
        }
    }
}

extension HashGroupWorryViewController: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return dataSource.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cell = tableView.dequeueReusableCell(withIdentifier: String(describing: WorryTableViewCell.self)) as! WorryTableViewCell
        let data = dataSource[indexPath.row]
        
        cell.titleView.text = data.title
        cell.bodyView.text = data.body
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
extension HashGroupWorryViewController: TagListViewDelegate {
    func tagPressed(_ title: String, tagView: TagView, sender: TagListView) {
        print("taped tag : \(title)")
    }
}

