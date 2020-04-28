//
//  AccessViewController.swift
//  ConectadoNaturaleza
//
//  Created by Marisol Reyes Espino on 24/04/20.
//  Copyright © 2020 Marisol Perez Rangel. All rights reserved.
//

import UIKit
import PromiseKit
import Alamofire

class LoginViewController: UIViewController {
    
    @IBOutlet weak var emailTextField: UITextField!
    @IBOutlet weak var passwordTextField: UITextField!
    @IBOutlet weak var segueSwitch: UISwitch!

    @IBAction func loginButtonAction(_ sender: Any) {
        getUserInfo()
        .done { json -> Void in
            print(json.id!)
            print(json.token!)
           
            if self.segueSwitch.isOn {
                //self.store.set(true, forKey: self.segueKey)
                //  theSwitch.setOn(UserDefaults.standard.bool(forKey: "onoroff"), animated: false)
                self.store.set(true, forKey: self.segueKey)
                print("segueSwitch")
                self.performSegue(withIdentifier: "login_segue", sender: nil)
            } else {
                self.store.set(false, forKey: self.segueKey)
                print("no segueSwitch")
                self.performSegue(withIdentifier: "login_segue", sender: nil)
                //self.store.removeObject(forKey: self.segueKey)
                
            }
            

        }
        .catch { error in
            let alert = UIAlertController(title: "Mensaje", message: "Datos invalidos", preferredStyle: .alert)
            alert.addAction(UIAlertAction(title: "OK", style: .default, handler: nil))
            self.present(alert, animated: true)
            print(error.localizedDescription)
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        getStore()
        
    }
    
    private let segueKey = "key"
    private let store = UserDefaults.standard
    
    func getStore()  {
        if store.object(forKey: segueKey) != nil {
            segueSwitch.isOn = store.bool(forKey: segueKey)
            print("viewdidload switch")
        } else {
            print("no viewdidload switch")
        }
    }

    func getUserInfo() -> Promise<LoginService> {
        return Promise { seal in
            
            guard let email = emailTextField.text, !email.isEmpty else {
                let alert = UIAlertController(title: "Mensaje", message: "Debes proporcionar un correo", preferredStyle: .alert)
                    alert.addAction(UIAlertAction(title: "OK", style: .default, handler: nil))
                    self.present(alert, animated: true)
                return
            }
            
            guard let password = passwordTextField.text, !password.isEmpty else {
                let alert = UIAlertController(title: "Mensaje", message: "Debes proporcionar una contraseña", preferredStyle: .alert)
                    alert.addAction(UIAlertAction(title: "OK", style: .default, handler: nil))
                    self.present(alert, animated: true)
                return
            }
            
            if email != "" && password != "" {
                let login = LoginStruct(email: email, password: password)
                AF.request("https://reqres.in/api/register", method: .post, parameters: login)
                    .validate()
                    .responseJSON { response in
                        switch response.result {
                        case .success(let json):
                            guard let json = json  as? [String: Any] else {
                            return seal.reject(AFError.responseValidationFailed(reason: .dataFileNil))
                            }
                            let login = LoginService(JSON: json)
                            seal.fulfill(login!)
                            print(login!)
                        case .failure(let error):
                            seal.reject(error)
                        }
                    }
                }
            }
        }
    }
