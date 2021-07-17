//
//  ChooseWorryOXView.swift
//  iOS
//
//  Created by 정지승 on 2021/07/06.
//

import UIKit

class ChooseWorryOXContentViewController: UIViewController {
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    // MARK: - IBAction
    @IBAction func OButtonTouchInside(_ sender: UIButton) {
        submit(answer: "O")
    }
    
    
    @IBAction func XButtonTouchInside(_ sender: UIButton) {
        submit(answer: "X")
    }
    
    // MARK: - Function
    
    func submit(answer: String) {
        guard let worryDetailViewController = self.parent as? WorryDetailViewController else {
            return
        }
        
        guard let worryDetailData = worryDetailViewController.data else {
            return
        }
        
        let selectedItem: Int
        
        if answer == "O" {
            selectedItem = 0
        } else {
            selectedItem = 1
        }
        
        guard let selectItemId = worryDetailData.choices?[selectedItem].id, let postId = worryDetailData.id else {
            return
        }
        
        let param: [String:Any] = ["post_id": postId, "choice_id": selectItemId]
        
        let alert: UIAlertController = UIAlertController(title: "알림", message: "\"\(answer)\"로 투표하시겠습니까?", preferredStyle: .alert)
        let alertDefaultAction: UIAlertAction = UIAlertAction(title: "확인", style: .default) { alert in
            APIRequest().request(url: APIRequest.votePostUrl, method: "POST", param: param) { success in
                guard success else {
                    print("ERROR: vote")
                    return
                }
                
                // ui 작업
            }
        }
        alert.addAction(alertDefaultAction)
        let alertCancelAction: UIAlertAction = UIAlertAction(title: "취소", style: .cancel, handler: nil)
        alert.addAction(alertCancelAction)
        self.present(alert, animated: true, completion: nil)
    }
}
