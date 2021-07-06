//
//  ChooseWorryOXView.swift
//  iOS
//
//  Created by 정지승 on 2021/07/06.
//

import UIKit

@IBDesignable
class ChooseWorryOXView: UIView {

    @IBOutlet weak var questionLable: UILabel!
    @IBOutlet weak var positiveButton: UIButton!
    @IBOutlet weak var negativeButton: UIButton!
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        self.loadView()
    }
    
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        self.loadView()
    }
    
    func loadView() {
        guard let view = loadViewFromNib(nib: "ChooseWorryOXView") else {
            return
        }
        
        addSubview(view)
        view.frame = bounds
        view.autoresizingMask = [.flexibleWidth, .flexibleHeight]
//        ball
    }
    
    func loadViewFromNib(nib: String) -> UIView? {
        let bundle = Bundle(for: type(of: self))
        let nib = UINib(nibName: nib, bundle: bundle)
        return nib.instantiate(withOwner: self, options: nil).first as? UIView
    }
}
