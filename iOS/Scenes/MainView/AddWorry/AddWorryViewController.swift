//
//  AddWorryViewController.swift
//  iOS
//
//  Created by 정지승 on 2021/07/08.
//

import UIKit
import TagListView

class AddWorryViewController: UIViewController {

    // MARK: - Properties
    private var nQueryContainerViewController: NQueryContainerViewController?
    // placeholder String
    private let placeholder: String = "고민이 뭐에요?"
    // tag
    private var tagsData: [String] = []
    
    // MARK: - IBOutlet
    @IBOutlet weak var worryTitle: UITextField!
    @IBOutlet weak var worryBody: UITextView!
    @IBOutlet weak var segControlView: UISegmentedControl!
    @IBOutlet weak var nQuestionContaioner: UIView!
    @IBOutlet weak var tagListView: TagListView!
    @IBOutlet weak var addTagText: UITextField!
    @IBOutlet weak var containerScrollView: UIScrollView!
    
    // MARk: - IBAction
    @IBAction func cancel(_ sender: UIButton) {
//        self.dismiss(animated: true, completion: nil)
        self.navigationController?.popViewController(animated: true)
    }
    
    @IBAction func addTagTouchInside(_ sender: UIButton) {
        if let text = addTagText.text?.lowercased() {
            if tagsData.count >= 5 {
                let alert = UIAlertController(title: "알림", message: "태그는 최대 5개 까지 추가 할 수 있습니다.", preferredStyle: .alert)
                let defaultAction = UIAlertAction(title: "확인", style: .default, handler: nil)
                alert.addAction(defaultAction)
                self.present(alert, animated: true, completion: nil)
            } else if !text.isEmpty && !tagsData.contains(text) {
                tagListView.addTag("#\(text)")
                tagsData.append("\(text)")
            }
            addTagText.text = nil
        }
        
        self.view.endEditing(true)
    }
    
    @IBAction func addWorry(_ sender: UIButton) {
        let worryType: WorryViewType
        let voteType: VoteType = .ONE
        var chooseItems: [String] = []
        
        // worry type
        if segControlView.selectedSegmentIndex == 0 {
            // OX
            worryType = .OX
            chooseItems.append(contentsOf: ["O", "X"])
        } else if segControlView.selectedSegmentIndex == 1 {
            // N
            worryType = .N
            if let items = nQueryContainerViewController?.dataSource {
                chooseItems = items
            }
        } else {
            print("Error")
            return
        }
        
        guard let title = self.worryTitle.text, let body = self.worryBody.text else {
            return
        }
        
        // Tags Default Value is Worry
        if tagsData.count == 0 {
            tagsData.append("고민")
        }
        
        var worryData: [String: Any] =
            ["title": title,
             "tag_names": tagsData,
             "body": body,
             "vote_type": voteType.rawValue,
             "worry_type": worryType.rawValue]
        
//        if let chooseItems = chooseItems {
            worryData["choice_names"] = chooseItems
//        }
        
        print(worryData)
        
        APIRequest().request(url: APIRequest.worryPostUrl, method: "POST", voType: WorryDataVO.self, param: worryData) { success, data in
            var msg: String = ""
            
            if success {
                msg = "고민 등록 성공"
                print(data)
            } else {
                msg = "고민 등록 실패"
            }
            
            DispatchQueue.main.async {
                let alert = UIAlertController(title: "알림", message: msg, preferredStyle: .alert)
                alert.addAction(UIAlertAction(title: "확인", style: .default, handler: nil))
                
                self.present(alert, animated: true, completion: nil)
            }
        }
        
        self.navigationController?.popViewController(animated: true)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // hide NQuestions
        nQuestionContaioner.isHidden = true
        containerScrollView.delegate = self
        
        setUpWorryBody()
        segControlView.addTarget(self, action: #selector(segValueChange(_:)), for: .valueChanged)
        
        setUpTagListView()
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        self.view.endEditing(true)
    }

    // MARK: - Navigation
    //   In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        let destination = segue.destination
        if let nQueryContainerViewController = destination as? NQueryContainerViewController {
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
    
    func setUpTagListView() {
        tagListView.delegate = self
        tagListView.textFont = UIFont.boldSystemFont(ofSize: 13)
    }
    
    // MARK: - Objc
    @objc func segValueChange(_ sender: UISegmentedControl) {
        if sender.selectedSegmentIndex == 0 {
            nQuestionContaioner.isHidden = true
        } else {
            nQuestionContaioner.isHidden = false
        }

    }
}

// MARK: - Extension and Delegate
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

// taglistview
extension AddWorryViewController: TagListViewDelegate {
    func tagRemoveButtonPressed(_ title: String, tagView: TagView, sender: TagListView) {
        let startIndex = title.index(title.startIndex, offsetBy: 1)
        let findString = title[startIndex...]
        let index = tagsData.firstIndex(of: String(findString))!
        tagsData.remove(at: index)
        
        tagListView.removeTag(title)
    }
}

// scrollview
extension AddWorryViewController: UIScrollViewDelegate {
    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        self.view.endEditing(true)
    }
}
