//
//  WorryDetailViewController.swift
//  iOS
//
//  Created by 정지승 on 2021/07/05.
//

import UIKit

class WorryDetailViewController: UIViewController {

    enum ViewType {
        case OX
        case N
    }
    
    // MARK: - Properties
    private var oxContentViewController: ChooseWorryOXContentViewController?
    private var nContentViewController: ChooseWorryNViewController?
    private var tabBarImage: [UIImage] = []
    var data: WorryDataVO?
    
    // MARK: - IBOutlet
    @IBOutlet weak var titleLable: UILabel!
    @IBOutlet weak var bodyLable: UILabel!
    @IBOutlet weak var interestNavItem: UIBarButtonItem!
    @IBOutlet weak var questionContentView: UIView!
    @IBOutlet weak var questionNContentView: UIView!
    
    
    // MARK: - LifeCycle
    override func viewDidLoad() {
        super.viewDidLoad()
        setUpNavigationItems()
        
        // add question controller
        loadQuestionTypeView(type: data?.viewType ?? .OX)
        
        // set current interest state
        interestNavItem.image = tabBarImage[0]
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        if questionContentView.isHidden {
            setUpNData()
        } else {
            setUpOXData()
        }
        
        
    }
    
    // MARK: - SetUp
    func setUpNData() {
        self.nContentViewController?.question = NQuestionVO(queries: ["a", "B", "c", "d", "e"], values: [true, false, false, false, false])
    }
    
    func setUpOXData() {
        self.oxContentViewController?.question = QuestionVO(question: "go mountain")
    }
    
    /// SetUp Navigation Items
    func setUpNavigationItems() {
        interestNavItem.target = self
        interestNavItem.action = #selector(interestNavItemTouch(_:))
        
        tabBarImage.append(UIImage(systemName: "star")!)
        tabBarImage.append(UIImage(systemName: "star.fill")!)
    }
    
    // MARK: Selector
    @objc func interestNavItemTouch(_ sender: UINavigationItem) {
        // update insterest info to server
        if interestNavItem.image == tabBarImage[0] {
            interestNavItem.image = tabBarImage[1]
        } else {
            interestNavItem.image = tabBarImage[0]
        }
        
    }
    
    // MARK: - Navigation
    //   In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        let destination = segue.destination
        if let oxContentViewController = destination as? ChooseWorryOXContentViewController {
            self.oxContentViewController = oxContentViewController
            oxContentViewController.view.translatesAutoresizingMaskIntoConstraints = false
        } else if let nContentViewController = destination as? ChooseWorryNViewController {
            self.nContentViewController = nContentViewController
            nContentViewController.view.translatesAutoresizingMaskIntoConstraints = false
        }
    }
    

}


extension WorryDetailViewController {
//    func setContainerViewController(storyboard: String, viewControllerId: String) -> UIViewController {
//
//        let storyboard = self.storyboard!
//        let VC = storyboard.instantiateViewController(withIdentifier: viewControllerId)
//        self.addChild(VC)
//
////        VC.view.autoresizingMask = [.flexibleHeight, .flexibleWidth]
////        VC.view.frame = questionContentView.bounds
//        questionContentView.addSubview((VC.view)!)
//        VC.didMove(toParent: self)
//
//        return VC
//    }
    
    func loadQuestionTypeView(type: ViewType) {
        if type == .OX {
            questionContentView.isHidden = false
            questionNContentView.isHidden = true
        } else {
            questionContentView.isHidden = true
            questionNContentView.isHidden = false
        }
    }
}
