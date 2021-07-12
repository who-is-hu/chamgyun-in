//
//  AttentionViewController.swift
//  iOS
//
//  Created by 정지승 on 2021/07/09.
//

import UIKit

class InterestViewController: UIViewController {
    // MARK: - Properties
    var tags: [TagVO] = []
    
    // MARK: - IBOutlet
    @IBOutlet weak var addTagTextView: UITextField!
    @IBOutlet weak var addTagButton: UIButton!
    @IBOutlet weak var tagTableView: UITableView!
    
    // MARK: - IBAction
    @IBAction func addTagButtonTouchInside(_ sender: UIButton) {
        if let tag = addTagTextView.text, !tag.isEmpty {
            tags.append(TagVO(id: 0, name: tag))
            self.tagTableView.reloadData()
            self.addTagTextView.text = nil
        }
        
        self.view.endEditing(true)
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        self.view.endEditing(true)
    }
    
    @IBAction func toBackTouchInside(_ sender: UIButton) {
        self.navigationController?.popViewController(animated: true)
    }
    
    @IBAction func updateTags(_ sender: UIButton) {
        // update tags info to server
        let tag_names = tags.map( { v -> String in v.name } )
        let param: [String: Any] = ["tag_names":tag_names]
        
        APIRequest().request(url: APIRequest.tagGetPatchUrl, method: "PATCH", voType: [TagVO].self, param: param) {success, data in
            if success {
                if let data = data as? [TagVO] {
                    print(data)
                }
            }
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        let nib: UINib = UINib(nibName: String(describing: InterestTableViewCell.self), bundle: nil)
        self.tagTableView.register(nib, forCellReuseIdentifier: String(describing: InterestTableViewCell.self))
        self.tagTableView.rowHeight = UITableView.automaticDimension
        self.tagTableView.estimatedRowHeight = 59
        
        self.tagTableView.delegate = self
        self.tagTableView.dataSource = self
        
        loadTagData()
    }
    
    func loadTagData() {
        APIRequest().request(url: APIRequest.tagGetPatchUrl, method: "GET", voType: [TagVO].self) { success, data in
            if success {
                if let data = data as? [TagVO] {
                    self.tags = data
                    
                    DispatchQueue.main.async {
                        self.tagTableView.reloadData()
                    }
                }
            }
        }
    }
}

// MARK: - extension and delegate
// tableview
extension InterestViewController: UITableViewDelegate {
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        print("selected row : \(indexPath.row)")
        tableView.deselectRow(at: indexPath, animated: true)
    }
}

extension InterestViewController: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return tags.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: String(describing: InterestTableViewCell.self)) as! InterestTableViewCell
        
        cell.tagText.text = tags[indexPath.row].name
        
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
