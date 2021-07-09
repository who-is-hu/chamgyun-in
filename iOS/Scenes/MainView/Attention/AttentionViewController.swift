//
//  AttentionViewController.swift
//  iOS
//
//  Created by 정지승 on 2021/07/09.
//

import UIKit

class AttentionViewController: UIViewController {
    // MARK: - Properties
    var tags: [String] = ["사과", "망고", "바나나"]
    
    // MARK: - IBOutlet
    @IBOutlet weak var addTagTextView: UITextField!
    @IBOutlet weak var addTagButton: UIButton!
    @IBOutlet weak var tagTableView: UITableView!
    
    // MARK: - IBAction
    @IBAction func addTagButtonTouchInside(_ sender: UIButton) {
        if let tag = addTagTextView.text, !tag.isEmpty {
            tags.append(tag)
            self.tagTableView.reloadData()
            self.addTagTextView.text = nil
        }
    }
    
    @IBAction func toBackTouchInside(_ sender: UIButton) {
        self.navigationController?.popViewController(animated: true)
    }
    
    @IBAction func updateTags(_ sender: UIButton) {
        // update tags info to server
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        let nib: UINib = UINib(nibName: String(describing: AttentionTableViewCell.self), bundle: nil)
        self.tagTableView.register(nib, forCellReuseIdentifier: String(describing: AttentionTableViewCell.self))
        self.tagTableView.rowHeight = UITableView.automaticDimension
        self.tagTableView.estimatedRowHeight = 59
        
        self.tagTableView.delegate = self
        self.tagTableView.dataSource = self
    }

}

// MARK: - extension and delegate
// tableview
extension AttentionViewController: UITableViewDelegate {
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        print("selected row : \(indexPath.row)")
        tableView.deselectRow(at: indexPath, animated: true)
    }
}

extension AttentionViewController: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return tags.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: String(describing: AttentionTableViewCell.self)) as! AttentionTableViewCell
        
        cell.tagText.text = tags[indexPath.row]
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        return true
    }
    
    func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCell.EditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            tags.remove(at: indexPath.row)
            tableView.reloadData()
        }
    }
}
