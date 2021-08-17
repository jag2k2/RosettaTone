package statemodels;

import uicomponents.TrainerState;
import utility.Maybe;

public class TrainerStateImp implements TrainerState {
    private boolean enabled = false;
    private Maybe<ConfigChangeNotifier> configChangeNotifier = new Maybe<>();

    @Override
    public void addConfigChangeNotifier(ConfigChangeNotifier configChangeNotifier){
        this.configChangeNotifier = new Maybe<>(configChangeNotifier);
    }

    @Override
    public String toString() {
       if(enabled)
           return "Enabled";
       else
           return "Disabled";
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof TrainerStateImp){
            TrainerStateImp toCompare = (TrainerStateImp) obj;
            return enabled == toCompare.enabled;
        }
        return false;
    }

    @Override
    public void enable() {
        if(!enabled){
            this.enabled = true;
            for (ConfigChangeNotifier notifier : configChangeNotifier){
                notifier.notifyObservers();
            }
        }
    }

    @Override
    public void disable() {
        if(enabled) {
            this.enabled = false;
            for (ConfigChangeNotifier notifier : configChangeNotifier) {
                notifier.notifyObservers();
            }
        }
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
