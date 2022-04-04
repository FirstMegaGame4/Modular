package fr.firstmegagame4.modular.modules;

public record ModuleCommandParameter(String name, String type) {
    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }
}
