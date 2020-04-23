//
//  ColorsTableViewCell.swift
//  ConectadoNaturaleza
//
//  Created by Marisol Perez Rangel on 22/04/20.
//  Copyright © 2020 Marisol Perez Rangel. All rights reserved.
//

import UIKit

class ColorsTableViewCell: UITableViewCell {

    // MARK: References
    
    @IBOutlet weak var colorView: UIView!
    @IBOutlet weak var serviceLabel: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        
        colorView.clipsToBounds = true 
    }
    
    func hexStringToUIColor (hex:String) -> UIColor {
        var cString:String = hex.trimmingCharacters(in: .whitespacesAndNewlines).uppercased()

        if (cString.hasPrefix("#")) {
            cString.remove(at: cString.startIndex)
        }

        if ((cString.count) != 6) {
            return UIColor.gray
        }

        var rgbValue:UInt64 = 0
        Scanner(string: cString).scanHexInt64(&rgbValue)

        return UIColor(
            red: CGFloat((rgbValue & 0xFF0000) >> 16) / 255.0,
            green: CGFloat((rgbValue & 0x00FF00) >> 8) / 255.0,
            blue: CGFloat(rgbValue & 0x0000FF) / 255.0,
            alpha: CGFloat(1.0)
        )
    }
    
    func setupCell(color: String, service: String) {
        let colorHex = hexStringToUIColor(hex: color)
        colorView.backgroundColor = colorHex
        serviceLabel.text = service
    }
    
}
