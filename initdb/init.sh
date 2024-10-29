#!/bin/bash
psql -U postgres << eof
  CREATE DATABASE one;
  CREATE DATABASE two;
eof