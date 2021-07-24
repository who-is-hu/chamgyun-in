//
//  ChartLegendTableViewCell.swift
//  iOS
//
//  Created by 정지승 on 2021/07/18.
//

import UIKit

class ChartLegendTableViewCell: UITableViewCell {
    @IBOutlet weak var legendColorButton: UIButton!
    @IBOutlet weak var legendLabel: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }
    
}
