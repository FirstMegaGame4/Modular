package fr.firstmegagame4.modular.modules;

public record ModuleFunctionParameter(String name, Object value) {
    public String getName() {
        return this.name;
    }

    public Object getValue() {
        return this.value;
    }
}
