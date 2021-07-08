//
//  NQueryContainerViewController.swift
//  iOS
//
//  Created by 정지승 on 2021/07/08.
//

import UIKit

class NQueryContainerViewController: UIViewController {
    // MARK: - Properties
    var dataSource: [String] = []
    
    // MARK: - IBOutlet
    @IBOutlet weak var tableView: UITableView!
    @IBOutlet weak var answerTextView: UITextField!
    
    // MARK: - IBAction
    @IBAction func answerAddTouchInside(_ sender: UIButton) {
        guard let answer = answerTextView.text, answer.isEmpty == false else {
            return
        }
        
        self.dataSource.append(answer)
        self.tableView.reloadData()
        self.answerTextView.text = nil
    }
    
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        let addWorryCellNib: UINib = UINib(nibName: String(describing: AddWorryTableViewCell.self), bundle: nil)
        
        tableView.register(addWorryCellNib, forCellReuseIdentifier: "addWorryCellNib")
        
        self.tableView.rowHeight = UITableView.automaticDimension
        self.tableView.estimatedRowHeight = 44
        
        self.tableView.delegate = self
        self.tableView.dataSource = self
        
    }
    
}

// MARK: - Delegate And Extension
// TableView
extension NQueryContainerViewController: UITableViewDelegate {
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
    }
}

extension NQueryContainerViewController: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {

        return dataSource.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cell = tableView.dequeueReusableCell(withIdentifier: "addWorryCellNib") as! AddWorryTableViewCell
        
        cell.worryLabelView.text = "\(indexPath.row+1). \(dataSource[indexPath.row])"
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        return true
    }
    
    func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCell.EditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            dataSource.remove(at: indexPath.row)
            tableView.reloadData()
        }
    }
    
}
