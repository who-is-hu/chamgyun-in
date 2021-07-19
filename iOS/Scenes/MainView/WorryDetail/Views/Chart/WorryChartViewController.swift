//
//  WorryChartViewController.swift
//  iOS
//
//  Created by 정지승 on 2021/07/18.
//

import UIKit
import Charts

class WorryChartViewController: UIViewController {
    // MARK: - Properties
    private var dataSource: [Legend] = []
    var votedQuestionIndex: Int = -1
    
    private struct Legend {
        let color: UIColor
        let text: String
    }
    
    // MARK: - IBOutlet
    @IBOutlet weak var chartView: PieChartView!
    @IBOutlet weak var tableView: UITableView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let nibId = String(describing: ChartLegendTableViewCell.self)
        let nib = UINib(nibName: nibId, bundle: nil)
        tableView.register(nib, forCellReuseIdentifier: nibId)
        tableView.rowHeight = UITableView.automaticDimension
        tableView.estimatedRowHeight = 44
        tableView.delegate = self
        tableView.dataSource = self
        
    }
    
    
    // MARK: - Function
    func customizeChart(dataPoints: [String], values: [Double], questions: [String]) {
        var dataEntries: [ChartDataEntry] = []
        for i in 0..<dataPoints.count {
          let dataEntry = PieChartDataEntry(value: values[i], label: dataPoints[i], data: dataPoints[i] as AnyObject)
          dataEntries.append(dataEntry)
        }
        
        let pieChartDataSet = PieChartDataSet(entries: dataEntries, label: nil)
        pieChartDataSet.drawValuesEnabled = false
        pieChartDataSet.colors = colorsOfCharts(numbersOfColor: dataPoints.count)
        
        let pieChartData = PieChartData(dataSet: pieChartDataSet)
        let format = NumberFormatter()
        format.numberStyle = .none
        let formatter = DefaultValueFormatter(formatter: format)
        pieChartData.setValueFormatter(formatter)
        
        loadLegend(colors: pieChartDataSet.colors, texts: questions)
        
        chartView.data = pieChartData
        chartView.legend.enabled = false
        chartView.rotationEnabled = false
    }
    
    private func loadLegend(colors: [UIColor], texts: [String]) {
        dataSource.removeAll()
        for i in 0..<colors.count {
            dataSource.append(Legend(color: colors[i], text: texts[i]))
        }
        print(dataSource)
        tableView.reloadData()
    }
    
    private func colorsOfCharts(numbersOfColor: Int) -> [UIColor] {
        var colorString: [String] = ["#93b327", "#f55354", "#f59b25", "#7fc638", "#754100", "#ffcc00"]
        var colors: [UIColor] = []
        
        guard numbersOfColor < colorString.count else {
            print("Overflow color")
            return []
        }
        
        for _ in 0..<numbersOfColor {
            let colorStr = colorString.randomElement()!
            if let idx = colorString.firstIndex(of: colorStr) {
                colorString.remove(at: idx)
                colors.append(UIColor(hexString: colorStr)!)
            }
        }
        return colors
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}

// MARK: - Extension
extension UIColor {
    convenience init?(hexString: String) {
        var chars = Array(hexString.hasPrefix("#") ? hexString.dropFirst() : hexString[...])
        switch chars.count {
        case 3: chars = chars.flatMap { [$0, $0] }; fallthrough
        case 6: chars = ["F","F"] + chars
        case 8: break
        default: return nil
        }
        self.init(red: .init(strtoul(String(chars[2...3]), nil, 16)) / 255,
                green: .init(strtoul(String(chars[4...5]), nil, 16)) / 255,
                 blue: .init(strtoul(String(chars[6...7]), nil, 16)) / 255,
                alpha: .init(strtoul(String(chars[0...1]), nil, 16)) / 255)
    }
}

extension WorryChartViewController: UITableViewDelegate {
    
}

extension WorryChartViewController: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return dataSource.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: String(describing: ChartLegendTableViewCell.self)) as! ChartLegendTableViewCell
        let data = self.dataSource[indexPath.row]
        
        cell.legendColorButton.tintColor = data.color
        if indexPath.row == self.votedQuestionIndex {
            cell.legendLabel.textColor = UIColor.red
        } else {
            cell.legendLabel.textColor = UIColor.black
        }
        cell.legendLabel.text = data.text
        
        return cell
    }
    
    
}
