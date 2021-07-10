//
//  AddWorryViewController.swift
//  iOS
//
//  Created by 정지승 on 2021/07/08.
//

import UIKit

class AddWorryViewController: UIViewController {

    // MARK: - Properties
    private var nQueryContainerViewController: NQueryContainerViewController?
    private var oxContainerViewController: OXContainerViewController?
    // placeholder String
    private let placeholder: String = "고민이 뭐에요?"
    
    // MARK: - IBOutlet
    @IBOutlet weak var worryTitle: UITextField!
    @IBOutlet weak var worryBody: UITextView!
    @IBOutlet weak var segControlView: UISegmentedControl!
    @IBOutlet weak var oxQuestionContainer: UIView!
    @IBOutlet weak var nQuestionContaioner: UIView!
    
    // MARk: - IBAction
    @IBAction func cancel(_ sender: UIButton) {
//        self.dismiss(animated: true, completion: nil)
        self.navigationController?.popViewController(animated: true)
    }
    
    @IBAction func addWorry(_ sender: UIButton) {
        print("asd")
        if segControlView.selectedSegmentIndex == 0 {
            // OX
            if let text = oxContainerViewController?.queryTextView.text {
                print(text)
            }
        } else if segControlView.selectedSegmentIndex == 1 {
            // N
            if let data = nQueryContainerViewController?.dataSource {
                print(data)
            }
        } else {
            print("asd")
        }
        
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()

        setUpWorryBody()
        segControlView.addTarget(self, action: #selector(segValueChange(_:)), for: .valueChanged)
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        self.view.endEditing(true)
    }

    // MARK: - Navigation
    //   In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        let destination = segue.destination
        if let oxContainerViewController = destination as? OXContainerViewController {
            self.oxContainerViewController = oxContainerViewController
            oxContainerViewController.view.translatesAutoresizingMaskIntoConstraints = false
        } else if let nQueryContainerViewController = destination as? NQueryContainerViewController {
            self.nQueryContainerViewController = nQueryContainerViewController
            nQueryContainerViewController.view.translatesAutoresizingMaskIntoConstraints = false
        }
    }
    
    // MARK: - SetUp
    func setUpWorryBody() {
        // placeholder
        worryBody.text = placeholder
        worryBody.textColor = .lightGray
        worryBody.delegate = self
    }
    
    // MARK: - Objc
    @objc func segValueChange(_ sender: UISegmentedControl) {
        if sender.selectedSegmentIndex == 0 {
            oxQuestionContainer.isHidden = false
            nQuestionContaioner.isHidden = true
        } else {
            oxQuestionContainer.isHidden = true
            nQuestionContaioner.isHidden = false
        }

    }
}

// MARK: - Delegate
/// UITextView Placeholder
extension AddWorryViewController: UITextViewDelegate {
    func textViewDidBeginEditing(_ textView: UITextView) {
        if textView.textColor == .lightGray {
            textView.text = nil
            textView.textColor = .black
        }
    }
    
    func textViewDidEndEditing(_ textView: UITextView) {
        if textView.text.isEmpty {
            worryBody.text = placeholder
            worryBody.textColor = .lightGray
        }
    }
}
