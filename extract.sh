#!/bin/bash
echo "Making msgs/messages.pot from all java sources"
find . -name '*.java' > .files-to-extract
xgettext -L Java -i --no-wrap --from-code utf-8 -F -f .files-to-extract \
         -ktrc:1c,2 -ktrcw:1c,2 -ktrcf:1c,2 -ktrcfw:1c,2 -ktr -ktrj -ktrfj \
         -ktrw -ktrf -ktrfw --force-po --omit-header -o data/i18n/messages.pot
rm .files-to-extract