/*
 * Copyright 2015 Len Payne <len.payne@lambtoncollege.ca>.
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
package tasks;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.concurrent.ManagedTask;
import javax.enterprise.concurrent.ManagedTaskListener;

/**
 *
 * @author Len Payne <len.payne@lambtoncollege.ca>
 */
public class ChangeTask implements Callable, ManagedTask, ManagedTaskListener {

    String input;

    public ChangeTask(String input) {
        this.input = input;
    }

    @Override
    public ManagedTaskListener getManagedTaskListener() {
        return this;
    }

    @Override
    public Map<String, String> getExecutionProperties() {
        return null;
    }

    @Override
    public String call() throws Exception {
        System.out.println("Called Callable");
        return input;
    }

    @Override
    public void taskSubmitted(Future<?> future, ManagedExecutorService executor, Object task) {
    }

    @Override
    public void taskAborted(Future<?> future, ManagedExecutorService executor, Object task, Throwable exception) {
    }

    @Override
    public void taskDone(Future<?> future, ManagedExecutorService executor, Object task, Throwable exception) {
        try {
            System.out.println("MOTD changed to " + future.get().toString());
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(ChangeTask.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void taskStarting(Future<?> future, ManagedExecutorService executor, Object task) {
    }
}
