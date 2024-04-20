import re

md_file_path = "./完成-2024-1.md"
md_output_file_path = md_file_path + ".txt"

if __name__ == '__main__':
    with open(md_file_path, "r", encoding="utf8") as f:
        md_content = f.read()

    if len(md_content.strip()) == 0:
        raise Exception("md文件为空")

    pattern = r"## (.*?)[\r\n]+([\s\S]*?)(?=(##|$))"
    matches = re.findall(pattern, md_content)

    res = []
    for match in matches:
        title = match[0]
        content = match[1]
        res.append(f"## {title}\n\n{content.strip()}\n")

    res.reverse()

    with open(md_output_file_path, "w", encoding="utf8") as f:
        for e in res:
            f.write(e)
            f.write("\n")

