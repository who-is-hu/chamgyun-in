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
    let refreshControl: UIRefreshControl = UIRefreshControl()
    
    // MARK: - IBOutlet
    @IBOutlet weak var titleLable: UILabel!
    @IBOutlet weak var bodyLable: UILabel!
    @IBOutlet weak var interestNavItem: UIBarButtonItem!
    @IBOutlet weak var questionContentView: UIView!
    @IBOutlet weak var questionNContentView: UIView!
    @IBOutlet weak var chartView: UIView!
    @IBOutlet weak var tagListView: TagListView!
    @IBOutlet weak var reportLabel: UILabel!
    @IBOutlet weak var scrollView: UIScrollView!
    @IBOutlet weak var endWorryVote: UIButton!
    @IBOutlet weak var endWorryVoteHeightConstraint: NSLayoutConstraint!
    
    // MARK: - IBAction
    @IBAction func chooseWorryForOwner(_ sender: UIButton) {
        guard let data = self.data else {
            return
        }
        
//        let alert = UIAlertController(title: "확인", message: "", preferredStyle: <#T##UIAlertController.Style#>)
        self.endWorryVote.isHidden = true
        showQuestionView(worryType: data.worryType, choices: data.choices)
    }
    
    // MARK: - LifeCycle
    override func viewDidLoad() {
        super.viewDidLoad()
        setUpNavigationItems()
        
        refreshControl.attributedTitle = NSAttributedString(string: "당겨서 새로고침")
        refreshControl.addTarget(self, action: #selector(pullToRefresh), for: .valueChanged)
        scrollView.refreshControl = refreshControl
        
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
            
            guard let data = data as? WorryDataDetailVO, let voted = data.voted else {
                return
            }
            
            self.data = data
            
            guard let owner = data.owner, let userId = AuthManager.shared.userInfo?.id, let boardState = data.state else {
                return
            }
            
            DispatchQueue.main.async {
                
                self.titleLable.text = data.title
                self.bodyLable.text = data.body
                self.loadTagsData(tags: data.splitTags)
                
                if owner == userId && boardState == "IN_PROGRESS" {
                    self.endWorryVote.isHidden = false
                    self.showSummaryView()
                } else {
                    self.endWorryVote.isHidden = true
//                    self.endWorryVoteHeightConstraint.constant = 0.0
                    for constraint in self.endWorryVote.constraints {
                        constraint.constant = 0.0
                    }
                    
                    if voted {
                        self.showSummaryView()
                    } else {
                        self.showQuestionView(worryType: data.worryType, choices: data.choices)
                    }
                }
                
                if self.refreshControl.isRefreshing {
                    self.refreshControl.endRefreshing()
                }
            }
               
        }
    }
    
    func showSummaryView() {
        self.reportLabel.text = "요약"
        self.chartView.isHidden = false
        
        if let postId = self.postId {
            self.loadMyChoicesItem(postId)
        }
        
        if let choices = self.data?.choices  {
            let votedNumberList: [Int] = choices.map( { $0.votedNumber ?? 0 } )
            let totalVotedNumber = votedNumberList.reduce(0, { $0 + $1 })
            
            if totalVotedNumber == 0 {
                self.worryChartViewController?.chartView.isHidden = true
                self.worryChartViewController?.tableView.isHidden = true
                self.worryChartViewController?.emptyMsgLabel.isHidden = false
            } else {
                self.worryChartViewController?.chartView.isHidden = false
                self.worryChartViewController?.tableView.isHidden = false
                self.worryChartViewController?.emptyMsgLabel.isHidden = true
                
                self.setUpChoicesItem(choices)
            }
        }
        
        self.questionContentView.isHidden = true
        self.questionNContentView.isHidden = true
    }
    
    func showQuestionView(worryType: String?, choices: [WorryChooseItem]?) {
        self.reportLabel.text = "질문"
        self.chartView.isHidden = true
        
        if let worryType = worryType, worryType == WorryViewType.OX.rawValue {
           self.loadQuestionTypeView(type: .OX)
       } else {
           self.loadQuestionTypeView(type: .N)
           
           if let choices = choices {
               let queries: [String] = choices.map({ $0.name! })
               self.setUpNData(queries: queries)
           }
       }
    }
    
    
    func setUpChoicesItem(_ choices: [WorryChooseItem]) {
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
    
    func loadMyChoicesItem(_ postId: Int) {
        APIRequest().request(url: "\(APIRequest.votePostUrl)/\(postId)/my-choices", method: "GET", voType: [WorryChooseItem].self) { success, data in
            guard success, let data = data as? [WorryChooseItem] else {
                print("ERROR : load voted item")
                return
            }
            
            if let choices = self.data?.choices {
                for worryItem in data {
                    for (index, choice) in choices.enumerated() {
                        if choice.id! == worryItem.id {
                            print("voted index = \(index)")
                            self.worryChartViewController?.votedQuestionIndex = index
                            DispatchQueue.main.async {
                                self.worryChartViewController?.tableView.reloadData()
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
    
    // MARK: objc
    @objc func interestNavItemTouch(_ sender: UINavigationItem) {
        // update insterest info to server
        if interestNavItem.image == tabBarImage[0] {
            interestNavItem.image = tabBarImage[1]
        } else {
            interestNavItem.image = tabBarImage[0]
        }
        
    }
    
    @objc func pullToRefresh(_ sender: UIRefreshControl) {
        self.loadWorryDetailData()
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
