# MiniJava-Compiler

A Java-based frontend for a MiniJava compiler, focused on parsing and semantic analysis using JavaCC and the Visitor pattern.

## Overview
This project implements parsing and type checking for MiniJava programs using JavaCC.  
It constructs an Abstract Syntax Tree (AST) and applies semantic checks to enforce Java’s type rules.

## Prerequisites
- **Java JDK 8+**  
- **Unix Shell** (bash or equivalent)

## Getting Started
 **Clone the repository**  
   ```bash
   git clone https://github.com/KHariHaraChaitanya/MiniJava-Compiler.git
   cd MiniJava-Compiler
   chmod +x Compile.sh
   ./Compile.sh
   ```
## Project Structure
MiniJava-Compiler/
├── javacc-6.0/          # JavaCC parser generator toolchain
├── bin/                 # Compiled parser/runtime artifacts
├── syntaxtree/          # AST node definitions
├── visitor/             # Visitor interfaces and implementations
├── tests/               # Sample MiniJava programs
├── Compile.sh           # Build and run script
└── Typecheck.java       # Main driver for parsing and type checking
Features
Grammar Parsing via JavaCC

AST Construction for MiniJava programs

Visitor Pattern to cleanly separate the AST data from semantic-check algorithms

Type Checking with detailed error reporting (type mismatches, undeclared identifiers, etc.)

Symbol Table for scope and declaration tracking

Extensible Architecture so you can add new analyses by writing additional visitor classes


