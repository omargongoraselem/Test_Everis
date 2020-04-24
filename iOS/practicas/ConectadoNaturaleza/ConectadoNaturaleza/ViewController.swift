//
//  ViewController.swift
//  ConectadoNaturaleza
//
//  Created by Marisol Reyes Espino on 17/04/20.
//  Copyright © 2020 Marisol Reyes Espino. All rights reserved.
//

struct Login: Encodable {
    let email: String
    let password: String
}

import UIKit
import Alamofire
import PromiseKit

class ViewController: UIViewController {
    
    @IBAction func ingresaButton(_ sender: Any) {
        getUserInfo()
        .done { json -> Void in
            if (json.id! != nil) && (json.token! != "") {
                print(json.id!)
                print(json.token!)
                /*let modalViewController = ColorViewController()
                modalViewController.modalPresentationStyle = .overCurrentContext
                self.present(modalViewController, animated: true, completion: nil)*/
            } else {
                
            }
        }
        .catch { error in
            print(error.localizedDescription)
        }
    }
    

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view.
    }
    
    
    let login = Login(email: "eve.holt@reqres.in", password: "password")
    let endpointString = "https://reqres.in/api/register"
    
    func getUserInfo() -> Promise<LoginService> {
        return Promise { seal in
            AF.request(endpointString, method: .post, parameters: login)
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
    
   /*
    func promiseLogin() -> Promise<[String: Any]>{
        return Promise { resolver in
            AF.request("https://reqres.in/api/register", method: .post, parameters: login).responseJSON {
            response in
            switch (response.result) {
                case .success(let data):
                    resolver.fulfill(data as! [String : Any])
                
                case .failure(let error):
                    print(error)
                    resolver.reject(error)
                    
                }
            }
        }
    }
    */
    /*
    private func fetchService() {
        
        let endpointString = "https://reqres.in/api/register"
        guard let endpoint = URL(string: endpointString) else {
            return
        }
        
        let login = Login(email: "eve.holt@reqres.in", password: "password")
        
        AF.request(endpoint, method: .post, parameters: login, encoder: JSONParameterEncoder.default).response {
            response in debugPrint(response)
            if let data = response.data {
                let json = String(data: data, encoding: String.Encoding.utf8)
                print("JSON Response: \(String(describing: json))")
                let loginService = LoginService(JSONString: json!)
                print(loginService?.id)
                print(loginService?.token)
            }
        }
        
    }*/
}


