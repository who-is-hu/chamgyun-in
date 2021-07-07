//
//  ChooseWorryNView.swift
//  iOS
//
//  Created by 정지승 on 2021/07/07.
//

import UIKit
import M13Checkbox

struct NQuestionVO {
    let queries: [String]
    var values: [Bool]
}

class ChooseWorryNViewController: UIViewController {
    var question: NQuestionVO?
    
    @IBOutlet weak var queryTableView: UITableView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let worryNTableCellNib: UINib = UINib(nibName: String(describing: ChooseWorryNTableViewCell.self), bundle: nil)
        
        self.queryTableView.register(worryNTableCellNib, forCellReuseIdentifier: "chooseWorryNTableViewCell")
        
        self.queryTableView.rowHeight = UITableView.automaticDimension
        self.queryTableView.estimatedRowHeight = 40
        
        self.queryTableView.delegate = self
        self.queryTableView.dataSource = self
        
    }
    
    override func viewWillAppear(_ animated: Bool) {
        guard let question = question else {
            return
        }
        
        print(question.queries)
        
    }
    
}

extension ChooseWorryNViewController: UITableViewDelegate {
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        print(indexPath.row)
        tableView.deselectRow(at: indexPath, animated: true)
        
        self.question?.values[indexPath.row].toggle()
        updateViewValues(row: indexPath.row)
    }
    
    func updateViewValues(row: Int) {
        let count: Int = self.question?.values.count ?? 0
        
        if self.question?.values[row] == true {
            for i in 0..<count {
                if i != row {
                    self.question?.values[i] = false
                }
            }
        }
        
        self.queryTableView.reloadData()
    }
}

extension ChooseWorryNViewController: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        
        return self.question?.queries.count ?? 0
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "chooseWorryNTableViewCell") as! ChooseWorryNTableViewCell
        
        cell.mCheckBox.setCheckState(((self.question?.values[indexPath.row])! ? .checked : .unchecked), animated: true)
        cell.mCheckBox.isEnabled = false
        cell.queryLable.text = self.question?.queries[indexPath.row]
        
        
        return cell
    }
}
