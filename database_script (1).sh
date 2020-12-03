#!/bin/bash

# colors
RED='\033[0;31m'
WH='\033[1;35m'
NC='\033[0m'
YL='\033[1;33m'
BL='\033[0;34m'

# Database setup
echo -e "${RED}[information]${NC}: ${BL}Starting dataBase setup script please wait..${NC}"

printf "\n"

read -r -p "Enter database name: " db_name

if [[ "$db_name" =~ ^[a-z] ]]; then

  printf "\n"

  echo -e "${RED}[information]${NC}: ${YL}Your database name is ${BL}${db_name}"

  printf "\n"

  echo -e "${RED}[information]${NC}: ${YL}Script running please wait....."

  printf "\n"

  sudo -u postgres createdb "$db_name"

  echo -e "${RED}[information]${NC}: ${YL}Your dataBase was successfully created"

  printf "\n"

  echo -e "${RED}[information]${NC}: ${YL}sudo psql ${db_name}${NC}"

  printf "\n"

  psql "$db_name"

else

  echo -e "${RED}Please use letters only!"
  exit 1

fi
