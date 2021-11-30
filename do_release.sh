#!/bin/bash

# 引数チェック
if [ $# -ne 1 ]; then
  echo "指定された引数は$#個です。" 1>&2
  CMDNAME=`basename $0`
  echo "Usage: $CMDNAME TAGNAME" 1>&2
  echo "ex: $CMDNAME v1.0.0" 1>&2
  exit 1
fi

TAGNAME=$1

SCRIPT_DIR=$(cd $(dirname $0); pwd)
#echo $SCRIPT_DIR
WORK_DIR=$SCRIPT_DIR/release_to_github_io
RELEASE_DIR=$SCRIPT_DIR/release_to_github_io/takke.github.io

cd $SCRIPT_DIR



echo "check here:"
echo "----------"
echo "git status:"
git status
echo "git diff:"
git diff
echo "----------"
echo "build.gradle および CHANGELOG.md は修正済みですか？(上記を確認してください)"
read -p "git add, commit, tag as $TAGNAME ok? (y/N): " yn
case "$yn" in
  [yY]*) ;;
  *) echo "abort"; exit;;
esac


echo "git add, commit"
git add build.gradle CHANGELOG.md
git commit -m "bump to $TAGNAME"

echo "git tag"
git tag $TAGNAME

echo "git push --tags"
git push --tags

echo "git push"
git push



echo "Release to github.io"

if [ -e $RELEASE_DIR ]; then
    echo "already clone"

    cd $RELEASE_DIR
    git pull
    cd $SCRIPT_DIR
else
    echo "first time"

    mkdir $WORK_DIR
    cd $WORK_DIR
    git clone git@github.com:takke/takke.github.io
    cd $SCRIPT_DIR
fi

read -p "release ok? (y/N): " yn
case "$yn" in
  [yY]*) echo "start";;
  *) echo "abort"; exit;;
esac

echo "generate artifacts to 'release_to_github_io/takke.github.io/maven/jp/takke/twitter4j-v2/...'"
echo "./gradlew clean publish -x test"
./gradlew clean publish -x test

cd $RELEASE_DIR
git status
git add maven
git status
git commit -m "twitter4j-v2 $TAGNAME"
git status

git push

cd $SCRIPT_DIR


echo "check files on: https://github.com/takke/takke.github.io"
echo "check files on: https://takke.github.io/maven/jp/takke/twitter4j-v2/twitter4j-v2-support/maven-metadata.xml"
