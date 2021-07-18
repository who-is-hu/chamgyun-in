//
//  WorryDetailViewController.swift
//  iOS
//
//  Created by 정지승 on 2021/07/05.
//

import UIKit
import TagListView

class WorryDetailViewController: UIViewController {
    // MARK: - Properties
    private var oxContentViewController: ChooseWorryOXContentViewController?
    private var nContentViewController: ChooseWorryNViewController?
    private var worryChartViewController: WorryChartViewController?
    private var tabBarImage: [UIImage] = []
    var postId: Int?
    var data: WorryDataDetailVO?
    
    // MARK: - IBOutlet
    @IBOutlet weak var titleLable: UILabel!
    @IBOutlet weak var bodyLable: UILabel!
    @IBOutlet weak var interestNavItem: UIBarButtonItem!
    @IBOutlet weak var questionContentView: UIView!
    @IBOutlet weak var questionNContentView: UIView!
    @IBOutlet weak var chartView: UIView!
    @IBOutlet weak var tagListView: TagListView!
    @IBOutlet weak var reportLabel: UILabel!
    
    
    // MARK: - LifeCycle
    override func viewDidLoad() {
        super.viewDidLoad()
        setUpNavigationItems()
        
        // set current interest state
        interestNavItem.image = tabBarImage[0]
        setUpTagListView()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        // add question controller
        loadWorryDetailData()
    }
    
    // MARK: - SetUp
    func setUpNData(queries: [String]) {
        let check: [Bool] = Array(repeating: false, count: queries.count)
        
        self.nContentViewController?.question = NQuestionVO(queries: queries, values: check)
        self.nContentViewController?.queryTableView.reloadData()
    }
    
    func setUpTagListView() {
        self.tagListView.delegate = self
        self.tagListView.textFont = UIFont.boldSystemFont(ofSize: 13)
    }
    
    /// SetUp Navigation Items
    func setUpNavigationItems() {
        interestNavItem.target = self
        interestNavItem.action = #selector(interestNavItemTouch(_:))
        
        tabBarImage.append(UIImage(systemName: "star")!)
        tabBarImage.append(UIImage(systemName: "star.fill")!)
    }
    
    func loadWorryDetailData() {
        guard let postId = self.postId else {
            return
        }
        
        let url: String = "\(APIRequest.worryPostUrl)/\(postId)"
        APIRequest().request(url: url, method: "GET", voType: WorryDataDetailVO.self) { success, data in
            guard success else {
                print("Error: \(url) request")
                return
            }
            
            if let data = data as? WorryDataDetailVO {
                self.data = data
                
                DispatchQueue.main.async {
                    
                    self.titleLable.text = data.title
                    self.bodyLable.text = data.body
                    self.loadTagsData(tags: data.splitTags)
                    
                    if let voted = data.voted {
                        
                        if voted {
                            self.reportLabel.text = "요약"
                            self.chartView.isHidden = false
                            
                            
                            if let choices = self.data?.choices  {
                                print(choices)
                                
                                let totalVoteNumber = choices.reduce(0) { res, next in
                                    return res + next.votedNumber!
                                }
                                
                                var voteLabels: [String] = []
                                var voteValues: [Double] = []
                                var voteQuestions: [String] = []
                                
                                for choice in choices {
                                    if let number = choice.votedNumber, let question = choice.name {
                                        print(Double(number) / Double(totalVoteNumber) * 100.0)
                                        let percent = Double(number) / Double(totalVoteNumber) * 100.0
                                        voteLabels.append(String(format: "%.2f%%", percent))
                                        voteValues.append(Double(number))
                                        voteQuestions.append("참여수 : \(number) - \(question)")
                                    }
                                }
                                
                                self.worryChartViewController?.customizeChart(dataPoints: voteLabels, values: voteValues, questions: voteQuestions)
                            }
                            
                            self.questionContentView.isHidden = true
                            self.questionNContentView.isHidden = true
                        } else {
                            self.reportLabel.text = "질문"
                            self.chartView.isHidden = true
                            
                            if data.worryType! == WorryViewType.OX.rawValue {
                               self.loadQuestionTypeView(type: .OX)
                           } else {
                               self.loadQuestionTypeView(type: .N)
                               
                               if let choices = data.choices {
                                   let queries: [String] = choices.map({ $0.name! })
                                   print(queries)
                                   self.setUpNData(queries: queries)
                               }
                           }
                        }
                        
                    }
                    
                    
                }
            }
            
            
        }
    }
    
    func loadTagsData(tags: [String]?) {
        self.tagListView.removeAllTags()
        if let tags = tags {
            self.tagListView.addTags(tags)
        }
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
        } else if let worryChartViewController = destination as? WorryChartViewController {
            self.worryChartViewController = worryChartViewController
            worryChartViewController.view.translatesAutoresizingMaskIntoConstraints = false
        }
    }
    

}

// MARK: - Extension And Delegate
extension WorryDetailViewController {
    func loadQuestionTypeView(type: WorryViewType) {
        if type == .OX {
            questionContentView.isHidden = false
            questionNContentView.isHidden = true
        } else {
            questionContentView.isHidden = true
            questionNContentView.isHidden = false
        }
    }
}

// TagListView
extension WorryDetailViewController: TagListViewDelegate {
    func tagPressed(_ title: String, tagView: TagView, sender: TagListView) {
        print("\(title) selected")
    }
}
