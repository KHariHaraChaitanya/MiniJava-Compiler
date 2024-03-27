#!/bin/bash

Tests="../Tests"

for file in "$Tests"/*
do
echo $file;
  java -cp .:../ Typecheck < $file
done

