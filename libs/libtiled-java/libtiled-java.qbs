import qbs

Product {
    Depends { name: "java"; required: false }
    condition: java.present

    filename: ["java.jar"]
    files: ["src/**/*.java"]

    Export {
        Depends { name: "java"; required: false }
        java.manifestClassPath: [product.targetName + ".jar"]
    }
}
