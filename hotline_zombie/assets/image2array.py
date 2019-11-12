from PIL import Image
import numpy as np

def load_image(infilename) :
    img = Image.open(infilename)
    img.load()
    data = np.asarray(img, dtype="int32")
    return data

def save_image(npdata, outfilename) :
    img = Image.fromarray(np.asarray( np.clip(npdata,0,255), dtype="uint8"), "L")
    img.save(outfilename)

img_arr = load_image('level1Map.png')
img_arr_shrinked = img_arr[0::32][0::32]

save_image(img_arr_shrinked, 'level1MapShrinked.png')
