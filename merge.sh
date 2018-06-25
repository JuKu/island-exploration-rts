#!/usr/bin/env bash
if [ -z "$1" ]; then
   echo "Please specify which language_country suffix(es) you want to merge"
   exit 1
fi

for lang in $*
do
  echo "Merging template file into $lang"
  msgmerge -i -F --no-wrap -o data/i18n/${lang}.po data/i18n/${lang}.po data/i18n/messages.pot
done