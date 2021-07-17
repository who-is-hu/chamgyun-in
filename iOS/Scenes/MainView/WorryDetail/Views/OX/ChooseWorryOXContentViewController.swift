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
    @IBOutlet weak var positiveButton: UIButton!
    @IBOutlet weak var negativeButton: UIButton!
    
    var question: QuestionVO?
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
}
