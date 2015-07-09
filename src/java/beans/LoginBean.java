/*
 * Copyright 2015 Len Payne <len.payne@lambtoncollege.ca>.
 * Updated 2015 Mark Russell <mark.russell@lambtoncollege.ca>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package beans;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateful;
import javax.enterprise.concurrent.ManagedExecutorService;

/**
 *
 * @author Len Payne <len.payne@lambtoncollege.ca>
 */
@Stateful
public class LoginBean {

    @Resource
    ManagedExecutorService executor;

    String name = "";

    public String getName() {
        return name;        
    }

    public void setName(String name) {
        this.name = name;
        
        // This is an arbitrarily-difficult task, to sample Runnables
        executor.execute(new Runnable() {

            @Override
            public void run() {
                try {
                    // Some Arbitrary Long-Running Process
                    for (int i = 0; i < 10000; i++) {
                        PrintWriter out = new PrintWriter(new File("out.txt"));
                        out.println(name);
                        out.close();
                    }
                    System.out.println("Finished Writing Name to a File and Deleting it Ten Thousand Times");

                } catch (FileNotFoundException ex) {
                    Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        // This is an arbitrarily-difficult task, to sample Callables
        Future<Date> future = executor.submit(new Callable<Date>() {

            @Override
            public Date call() {
                try {
                    // Some Arbitrary Long-Running Process
                    for (int i = 0; i < 10000; i++) {
                        PrintWriter out = new PrintWriter(new File("out.txt"));
                        out.println(name);
                        out.close();
                    }
                    System.out.println("Finished Writing Name to a File and Deleting it Ten Thousand Times");

                } catch (FileNotFoundException ex) {
                    Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
                }
                return new Date();
            }
        });
        
        // Synchronously wait for the Callable to finish and get its result
        try {
            System.out.println("Results Retrieved at: " + future.get());
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }        
        
    }

}
