/*
Copyright (c) 2012, Ulrik Pagh Schultz, University of Southern Denmark
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met: 

1. Redistributions of source code must retain the above copyright notice, this
   list of conditions and the following disclaimer. 
2. Redistributions in binary form must reproduce the above copyright notice,
   this list of conditions and the following disclaimer in the documentation
   and/or other materials provided with the distribution. 

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

The views and conclusions contained in the software and documentation are those
of the authors and should not be interpreted as representing official policies, 
either expressed or implied, of the University of Southern Denmark.
*/

package statemachine.year1.library;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import quickqui.QuickGUI;
import statemachine.year1.cdplayer.CDPlayer1.ControlGUI;
import statemachine.year2.framework.MachineExecutor;
import statemachine.year3.dsl.GenericState;

/**
 * Generic infrastructure for having a GUI for a state machine
 * @author ups
 */
public abstract class GraphicalMachine<T extends IRuntimeState> implements ActionListener, Observer {
	/**
	 * The GUI of the machine
	 */
    protected QuickGUI gui;
    /**
     * The state machine being driven by the GUI
     */
    protected IMachine<T> machine;
    /**
     * The action name that triggers initialize on the state machine
     */
    private String powerOnCommand;
    
    /**
     * Create the graphical state machine
     * @param model the GUI
     * @param machine the statemachine
     * @param powerOnCommand the command tha triggers state machine initialization
     */
    public GraphicalMachine(QuickGUI.GUIModel model, IMachine<T> machine, String powerOnCommand) {
        this.gui = new QuickGUI(model,this);
        this.machine = machine;
        this.powerOnCommand = powerOnCommand;
        this.machine.addObserver(this);
    }

	/**
     * Handle events generated from the GUI
     */
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals(powerOnCommand))
            machine.initialize();
        else {
            Event event = new Event(e.getActionCommand());
            machine.processEvent(event);
        }
    }

    /**
     * Handle events generated by the state machine
     */
    @Override
    public void update(Observable o, Object arg) {
        if(!(o==machine)) throw new Error("Inconsistent observer notification");
        this.update();
    }

    /**
     * Override in subclasses to be notified when state machine has been updated
     */
    protected abstract void update();

}
