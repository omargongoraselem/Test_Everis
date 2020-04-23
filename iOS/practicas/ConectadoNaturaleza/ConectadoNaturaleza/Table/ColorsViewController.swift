//
//  ColorsViewController.swift
//  ConectadoNaturaleza
//
//  Created by Marisol Reyes Espino on 22/04/20.
//  Copyright © 2020 Marisol Perez Rangel. All rights reserved.
//

import UIKit

class ColorsViewController: UIViewController {
    
    @IBOutlet weak var colorTableView: UITableView!
    
    var dataSource = [
        "cerulean" : "#98B2D1",
        "fuchsia rose" : "#C74375",
        "true red" : "#BF1932",
        "aqua sky" : "#7BC4C4",
        "tigerlily" : "#E2583E",
        "blue turquoise" : "#53B0AE"
    ]
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        colorTableView.dataSource = self
        colorTableView.register(UINib(nibName: "ColorsTableViewCell", bundle: nil), forCellReuseIdentifier: "ColorsTableViewCell")
        colorTableView.delegate = self
        
    }
}

// MARK: UITableViewDelegate
    extension ColorsViewController: UITableViewDelegate {
        func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
            print("La celda \(indexPath.row)")
        }
    }

// MARK: UITableViewDataSource
   extension ColorsViewController: UITableViewDataSource {
        func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
            return dataSource.count
            
        }
        func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
            let cell = colorTableView.dequeueReusableCell(withIdentifier: "ColorsTableViewCell", for: indexPath)
            
            let key = Array(dataSource.keys)
            let value = Array(dataSource.values)
            
            if let newCell = cell as? ColorsTableViewCell {
                newCell.setupCell(color: value[indexPath.row], service: key[indexPath.row])
            }
            
            return cell
        }
   }

