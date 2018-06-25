if [ -z "$1" ]; then
   echo "Please specify which language_country suffix(es) you want to merge"
   exit 1
fi

for lang in $*
do
  echo "Initializing template file for $lang"
  if [ -f data/i18n/$lang.po ]; then
     echo "data/i18n/$lang.po exists! You probably wanted merge"
     exit
  fi
  msginit --no-translator -l $lang --no-wrap -o data/i18n/${lang}.po -i data/i18n/messages.pot
done