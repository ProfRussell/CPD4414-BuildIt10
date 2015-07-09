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

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 *
 * @author Len Payne <len.payne@lambtoncollege.ca>
 */
@MessageDriven(mappedName = "jms/Queue")
public class MOTDChangeBean implements MessageListener {
    @EJB
    MOTDBean motd;
    
    @EJB
    LoggingBean log;

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                String newMOTD = ((TextMessage)message).getText();
                motd.setMotd(newMOTD);
            }
        } catch (JMSException ex) {
            log.log("Error with JMS: " + ex.getMessage());
        }
    }
    
}
