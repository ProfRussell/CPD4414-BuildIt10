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

import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.validation.constraints.Size;
import tasks.ChangeTask;

/**
 *
 * @author Len Payne <len.payne@lambtoncollege.ca>
 */
@Singleton
public class MOTDBean {

    @Resource
    ManagedScheduledExecutorService executor;

    @Size(min=3)
    private String motd = "Hello World!";

    public String getMotd() {
        return motd;
    }

    public void setMotd(String motd) {
        this.motd = motd;
        // Call an asynchronous task that performs another task on completion
        executor.submit(new ChangeTask(motd));
        // Call a scheduled task, that wipes out the value after 30 seconds
        executor.schedule(new WipeTask(), 30, TimeUnit.SECONDS);
    }

    private class WipeTask implements Runnable {

        @Override
        public void run() {
            motd = "Hello World!";
        }
        
    }
}
