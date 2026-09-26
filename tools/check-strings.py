#!/usr/bin/env python3
import os
import sys
import xml.etree.ElementTree as ET

def get_string_keys(xml_path):
    if not os.path.exists(xml_path):
        print(f"Error: File not found: {xml_path}")
        return set()
    tree = ET.parse(xml_path)
    root = tree.getroot()
    keys = set()
    for elem in root.findall('string'):
        name = elem.get('name')
        if name:
            keys.add(name)
    return keys

def main():
    script_dir = os.path.dirname(os.path.abspath(__file__))
    project_root = os.path.dirname(script_dir)

    default_xml = os.path.join(project_root, "app/src/main/res/values/strings.xml")
    ru_xml = os.path.join(project_root, "app/src/main/res/values-ru/strings.xml")

    if not os.path.exists(default_xml) or not os.path.exists(ru_xml):
        print("Error: strings.xml missing in default or values-ru location.")
        sys.exit(1)

    print(f"Checking default strings: {default_xml}")
    print(f"Checking RU strings: {ru_xml}")

    default_keys = get_string_keys(default_xml)
    ru_keys = get_string_keys(ru_xml)

    print(f"Default keys count: {len(default_keys)}")
    print(f"RU keys count: {len(ru_keys)}")

    missing_in_ru = default_keys - ru_keys
    missing_in_default = ru_keys - default_keys

    has_error = False
    if missing_in_ru:
        print(f"Keys missing in RU locale: {missing_in_ru}")
        has_error = True
    if missing_in_default:
        print(f"Keys missing in Default locale: {missing_in_default}")
        has_error = True

    if has_error:
        print("String parity check FAILED!")
        sys.exit(1)
    else:
        print("String parity check PASSED! Both locales match 100%.")

if __name__ == "__main__":
    main()
