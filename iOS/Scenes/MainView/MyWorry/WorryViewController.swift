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
    
    func loadWorryData() {
        dataSource.removeAll()
        
        // load my worry boards
        if loadType == .MyWorry {
            dataSource.append(WorryDataVO(id: 0, title: "MyWorry1", body: "packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like)", viewCount: 3, tags: ["a", "b", "c"], viewType: .N))
            dataSource.append(WorryDataVO(id: 0, title: "MyWorry2", body: "packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like)", viewCount: 3, tags: ["a", "b", "c"], viewType: .N))
            dataSource.append(WorryDataVO(id: 0, title: "MyWorry3", body: "packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like)", viewCount: 3, tags: ["a", "b", "c"], viewType: .N))
            dataSource.append(WorryDataVO(id: 0, title: "MyWorry4", body: "packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like)", viewCount: 3, tags: ["a", "b", "c"], viewType: .N))
            dataSource.append(WorryDataVO(id: 0, title: "MyWorry4", body: "packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like)", viewCount: 3, tags: ["a", "b", "c"], viewType: .N))
            dataSource.append(WorryDataVO(id: 0, title: "MyWorry4", body: "packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like)", viewCount: 3, tags: ["a", "b", "c"], viewType: .N))
            dataSource.append(WorryDataVO(id: 0, title: "MyWorry4", body: "packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like)", viewCount: 3, tags: ["a", "b", "c"], viewType: .N))
            
        } else {
            // load answer worry boards
            dataSource.append(WorryDataVO(id: 0, title: "AnsWorry1", body: "packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like)", viewCount: 3, tags: ["a", "b", "c"], viewType: .N))
        }
        
        worryTableView.reloadData()
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
        let cell = tableView.dequeueReusableCell(withIdentifier: String(describing: WorryTableViewCell.self), for: indexPath) as! WorryTableViewCell
        
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

// taglistview
extension WorryViewController: TagListViewDelegate {
    func tagPressed(_ title: String, tagView: TagView, sender: TagListView) {
        print("tab pressed title : \(title)")
    }
}

