//
//  ViewController.swift
//  ConectadoNaturaleza
//
//  Created by Marisol Reyes Espino on 17/04/20.
//  Copyright © 2020 Marisol Reyes Espino. All rights reserved.
//


import UIKit
import Alamofire
import PromiseKit

class ViewController: UIViewController {
    
    @IBAction func ingresaButton(_ sender: Any) {
        /*let loginView  = LoginViewController()
            loginView.getStore()*/
        
        if UserDefaults.standard.bool(forKey: "loginKey") == true {
            /*
           let home = self.storyboard?.instantiateViewController(withIdentifier: "StoryColor") as! ColorViewController
           self.navigationController?.pushViewController(home, animated: false)
             */
            self.performSegue(withIdentifier: "colorSegue", sender: nil)
        } else {
            self.performSegue(withIdentifier: "loginSegue", sender: nil)
        }
               
    }
    

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
    }
}


