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
    // MARK: - Properties
    var question: NQuestionVO?
    var selectedItem: Int = -1
    
    // MARK: - IBOutlet
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
    
    // MARK: - IBAction
    @IBAction func submitButtonTouchInside(_ sender: UIButton) {
        guard selectedItem >= 0 else {
            let alert: UIAlertController = UIAlertController(title: "알림", message: "질문에 답해주세요.", preferredStyle: .alert)
            let alertAction: UIAlertAction = UIAlertAction(title: "확인", style: .default, handler: nil)
            alert.addAction(alertAction)
            self.present(alert, animated: true, completion: nil)

            return
        }
        
        guard let worryDetailViewController = self.parent as? WorryDetailViewController else {
            return
        }
        
        guard let worryDetailData = worryDetailViewController.data else {
            return
        }
        
        guard let selectItemId = worryDetailData.choices?[selectedItem].id, let postId = worryDetailData.id else {
            return
        }
        
        let param: [String:Any] = ["post_id": postId, "choice_id": selectItemId]
        
        let alert: UIAlertController = UIAlertController(title: "알림", message: "투표하시겠습니까?", preferredStyle: .alert)
        let alertDefaultAction: UIAlertAction = UIAlertAction(title: "확인", style: .default) { alert in
            APIRequest().request(url: APIRequest.votePostUrl, method: "POST", param: param) { success in
                guard success else {
                    print("ERROR: vote")
                    return
                }
                
                // ui 작업
                worryDetailViewController.loadWorryDetailData()
            }
        }
        alert.addAction(alertDefaultAction)
        let alertCancelAction: UIAlertAction = UIAlertAction(title: "취소", style: .cancel, handler: nil)
        alert.addAction(alertCancelAction)
        self.present(alert, animated: true, completion: nil)
    }
    
    
}

// MARK: - Extension and Delegate
extension ChooseWorryNViewController: UITableViewDelegate {
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        print(indexPath.row)
        tableView.deselectRow(at: indexPath, animated: true)
        
        self.question?.values[indexPath.row].toggle()
        selectedItem = indexPath.row
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
