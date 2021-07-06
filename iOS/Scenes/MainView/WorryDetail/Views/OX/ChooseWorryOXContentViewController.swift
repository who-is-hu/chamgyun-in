//
//  ChooseWorryOXView.swift
//  iOS
//
//  Created by 정지승 on 2021/07/06.
//

import UIKit

struct QuestionVO {
    let question: String
}

class ChooseWorryOXContentViewController: UIViewController {
    
    @IBOutlet weak var questionLabelView: UILabel!
    @IBOutlet weak var positiveButton: UIButton!
    @IBOutlet weak var negativeButton: UIButton!
    
    var question: QuestionVO?
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        updateView()
    }
    
    func updateView() {
        guard let question = question else {
            return
        }
        
        questionLabelView.text = question.question
    }
}
