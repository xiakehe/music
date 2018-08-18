package com.example.study_music.com.xkh.music.main.index.bean;

import java.io.Serializable;
import java.util.List;


public class IndexBean implements Serializable {

    private ResultBeanXXXXXXXXXXXXXX result;
    private int error_code;
    private List<ModuleBean> module;

    public ResultBeanXXXXXXXXXXXXXX getResult() {
        return result;
    }


    public void setResult(ResultBeanXXXXXXXXXXXXXX result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public void setModule(List<ModuleBean> module) {
        this.module = module;
    }

    @Override
    public String toString() {
        return "IndexBean{" +
                "result=" + result +
                ", error_code=" + error_code +
                ", module=" + module +
                '}';
    }

    public List<ModuleBean> getModule() {
        return module;
    }


    public static class ResultBeanXXXXXXXXXXXXXX implements Serializable {

        private Mix9Bean mix_9;
        private Mix2Bean mix_2;
        private Mix22Bean mix_22;
        private FocusBean focus;
        private ShowListBean show_list;
        private EntryBean entry;
        private SceneBean scene;
        private Mix1Bean mix_1;
        private RecsongBean recsong;
        private RadioBean radio;
        private NewSongBean new_song;
        private DiyBean diy;
        private KingBean king;
        private Mod29Bean mod_29;
        private Mix29Bean mix_29;
        private Mod27Bean mod_27;


        @Override
        public String toString() {
            return "ResultBeanXXXXXXXXXXXXXX{" +
                    "mix_9=" + mix_9 +
                    ", focus=" + focus +
                    ", mix_22=" + mix_22 +
                    ", show_list=" + show_list +
                    ", entry=" + entry +
                    ", scene=" + scene +
                    ", mix_1=" + mix_1 +
                    ", recsong=" + recsong +
                    ", radio=" + radio +
                    ", new_song=" + new_song +
                    ", diy=" + diy +
                    ", king=" + king +
                    ", mod_29=" + mod_29 +
                    ", mix_29=" + mix_29 +
                    ", mod_27=" + mod_27 +
                    ", mix_2=" + mix_2 +
                    '}';
        }

        public Mod27Bean getMod_27() {
            return mod_27;
        }

        public void setMod_27(Mod27Bean mod_27) {
            this.mod_27 = mod_27;
        }

        public Mix29Bean getMix_29() {
            return mix_29;
        }

        public void setMix_29(Mix29Bean mix_29) {
            this.mix_29 = mix_29;
        }

        public Mod29Bean getMod_29() {
            return mod_29;
        }

        public void setMod_29(Mod29Bean mod_29) {
            this.mod_29 = mod_29;
        }

        public Mix2Bean getMix_2() {
            return mix_2;
        }

        public void setMix_2(Mix2Bean mix_2) {
            this.mix_2 = mix_2;
        }


        public Mix9Bean getMix_9() {
            return mix_9;
        }

        public void setMix_9(Mix9Bean mix_9) {
            this.mix_9 = mix_9;
        }

        public FocusBean getFocus() {
            return focus;
        }

        public void setFocus(FocusBean focus) {
            this.focus = focus;
        }

        public Mix22Bean getMix_22() {
            return mix_22;
        }

        public void setMix_22(Mix22Bean mix_22) {
            this.mix_22 = mix_22;
        }

        public ShowListBean getShow_list() {
            return show_list;
        }

        public void setShow_list(ShowListBean show_list) {
            this.show_list = show_list;
        }

        public EntryBean getEntry() {
            return entry;
        }

        public void setEntry(EntryBean entry) {
            this.entry = entry;
        }

        public SceneBean getScene() {
            return scene;
        }

        public void setScene(SceneBean scene) {
            this.scene = scene;
        }


        public Mix1Bean getMix_1() {
            return mix_1;
        }

        public void setMix_1(Mix1Bean mix_1) {
            this.mix_1 = mix_1;
        }

        public RecsongBean getRecsong() {
            return recsong;
        }

        public void setRecsong(RecsongBean recsong) {
            this.recsong = recsong;
        }

        public RadioBean getRadio() {
            return radio;
        }

        public void setRadio(RadioBean radio) {
            this.radio = radio;
        }

        public NewSongBean getNew_song() {
            return new_song;
        }

        public void setNew_song(NewSongBean new_song) {
            this.new_song = new_song;
        }

        public DiyBean getDiy() {
            return diy;
        }

        public void setDiy(DiyBean diy) {
            this.diy = diy;
        }


        public KingBean getKing() {
            return king;
        }

        public void setKing(KingBean king) {
            this.king = king;
        }

        public static class Mod27Bean implements Serializable {
            private int error_code;
            private List<Mod27Bean.ResultBean> result;

            public List<ResultBean> getResult() {
                return result;
            }

            public void setResult(List<ResultBean> result) {
                this.result = result;
            }

            public static class ResultBean implements Serializable {
                private String desc;
                private String pic;
                private String type_id;
                private int type;
                private String title;
                private int tip_type;
                private String author;

                public String getDesc() {
                    return desc;
                }

                public void setDesc(String desc) {
                    this.desc = desc;
                }

                public String getPic() {
                    return pic;
                }

                public void setPic(String pic) {
                    this.pic = pic;
                }

                public String getType_id() {
                    return type_id;
                }

                public void setType_id(String type_id) {
                    this.type_id = type_id;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public int getTip_type() {
                    return tip_type;
                }

                public void setTip_type(int tip_type) {
                    this.tip_type = tip_type;
                }

                public String getAuthor() {
                    return author;
                }

                public void setAuthor(String author) {
                    this.author = author;
                }
            }
        }

        public static class Mix29Bean implements Serializable {
            private int error_code;
            private List<Mix29Bean.ResultBean> result;

            public static class ResultBean implements Serializable {
                private String desc;
                private String pic;
                private String type_id;
                private int type;
                private String title;
                private int tip_type;
                private String author;

                public String getDesc() {
                    return desc;
                }

                public void setDesc(String desc) {
                    this.desc = desc;
                }

                public String getPic() {
                    return pic;
                }

                public void setPic(String pic) {
                    this.pic = pic;
                }

                public String getType_id() {
                    return type_id;
                }

                public void setType_id(String type_id) {
                    this.type_id = type_id;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public int getTip_type() {
                    return tip_type;
                }

                public void setTip_type(int tip_type) {
                    this.tip_type = tip_type;
                }

                public String getAuthor() {
                    return author;
                }

                public void setAuthor(String author) {
                    this.author = author;
                }
            }

            public int getError_code() {
                return error_code;
            }

            public void setError_code(int error_code) {
                this.error_code = error_code;
            }

            public List<ResultBean> getResult() {
                return result;
            }

            public void setResult(List<ResultBean> result) {
                this.result = result;
            }
        }

        public static class Mix9Bean implements Serializable {
            /**
             * error_code : 22000
             * result : [{"desc":"","pic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_14881961592cabd31510b72889843bd232e71e6150.jpg","type_id":"http://y.baidu.com/tbang","type":4,"title":"T榜第一期年榜决选","tip_type":0,"author":""},{"desc":"","pic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_148842760200a2d30ad296101a488dd781929454dc.jpg","type_id":"http://y.baidu.com/cms/topic/webapp/2017/xiaolaohu/index.html","type":4,"title":"独家专访\u201c小老虎\u201d","tip_type":0,"author":""},{"desc":"","pic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_1488178404cd220d60e2b0b2f98f66987f3c5ff44b.jpg","type_id":"354332843","type":0,"title":"悠悠古调，如约而至","tip_type":0,"author":""}]
             */

            private int error_code;
            private List<ResultBean> result;

            public int getError_code() {
                return error_code;
            }

            public void setError_code(int error_code) {
                this.error_code = error_code;
            }

            public List<ResultBean> getResult() {
                return result;
            }

            public void setResult(List<ResultBean> result) {
                this.result = result;
            }

            @Override
            public String toString() {
                return "Mix9Bean{" +
                        "error_code=" + error_code +
                        ", result=" + result +
                        '}';
            }

            public static class ResultBean implements Serializable {
                /**
                 * desc :
                 * pic : http://business.cdn.qianqian.com/qianqian/pic/bos_client_14881961592cabd31510b72889843bd232e71e6150.jpg
                 * type_id : http://y.baidu.com/tbang
                 * type : 4
                 * title : T榜第一期年榜决选
                 * tip_type : 0
                 * author :
                 */

                private String desc;
                private String pic;
                private String type_id;
                private int type;
                private String title;
                private int tip_type;
                private String author;

                public String getType_id() {
                    return type_id;
                }

                public String getAuthor() {
                    return author;
                }

                public String getPic() {
                    return pic;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                @Override
                public String toString() {
                    return "ResultBean{" +
                            "desc='" + desc + '\'' +
                            ", pic='" + pic + '\'' +
                            ", type_id='" + type_id + '\'' +
                            ", type=" + type +
                            ", title='" + title + '\'' +
                            ", tip_type=" + tip_type +
                            ", author='" + author + '\'' +
                            '}';
                }
            }
        }

        public static class Mod29Bean implements Serializable {
            private int error_code;
            private List<Mod29Bean.ResultBeanX> result;

            public List<ResultBeanX> getResult() {
                return result;
            }

            public static class ResultBeanX implements Serializable {
                private String desc;
                private String pic;
                private String type_id;
                private int type;
                private String title;
                private int tip_type;
                private String author;



                public String getPic() {
                    return pic;
                }


                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getType_id() {
                    return type_id;
                }
                //                public int getTip_type() {
//                    return tip_type;
//                }
//
//                public void setTip_type(int tip_type) {
//                    this.tip_type = tip_type;
//                }
//
//                public String getAuthor() {
//                    return author;
//                }
//
//                public void setAuthor(String author) {
//                    this.author = author;
//                }
            }

        }

        public static class FocusBean implements Serializable {
            /**
             * error_code : 22000
             * result : [{"randpic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_14885355757dfa7fd7ab2c300433d381dcdf4713c1.jpg","code":"325272266","mo_type":2,"type":2,"is_publish":"111111","randpic_iphone6":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_14885355757dfa7fd7ab2c300433d381dcdf4713c1.jpg","randpic_desc":"Green Light"},{"randpic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_1488520099caa081bb4eae93b92581475d9c14a8d7.jpg","code":"http://music.baidu.com/h5pc/spec_detail?id=172&columnid=88","mo_type":4,"type":6,"is_publish":"111111","randpic_iphone6":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_1488520099caa081bb4eae93b92581475d9c14a8d7.jpg","randpic_desc":"华语乐坛的那些幕后大师们\u2014陈小霞（上）"},{"randpic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_1488508408be15e6a434fb4f847ae3f82f3a580fc6.jpg","code":"533370111","mo_type":2,"type":2,"is_publish":"111111","randpic_iphone6":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_1488508408be15e6a434fb4f847ae3f82f3a580fc6.jpg","randpic_desc":"爱又爱"},{"randpic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_14883513291b6d3c35953ee8a240d4d28f7e8a9635.jpg","code":"http://music.baidu.com/cms/webview/bigwig/xusong0229/index.html","mo_type":4,"type":6,"is_publish":"111111","randpic_iphone6":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_14883513291b6d3c35953ee8a240d4d28f7e8a9635.jpg","randpic_desc":"许嵩青年晚报演唱会"},{"randpic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_14885265473476d3353b56fb91c17eb64283d67003.jpg","code":"http://music.baidu.com/cms/webview/bigwig/20170303/index.html","mo_type":4,"type":6,"is_publish":"111111","randpic_iphone6":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_14885265473476d3353b56fb91c17eb64283d67003.jpg","randpic_desc":"广告"}]
             */

            private int error_code;
            private List<ResultBeanX> result;

//            public int getError_code() {
//                return error_code;
//            }
//
//            public void setError_code(int error_code) {
//                this.error_code = error_code;
//            }

            public List<ResultBeanX> getResult() {
                return result;
            }

//            public void setResult(List<ResultBeanX> result) {
//                this.result = result;
//            }

            public static class ResultBeanX implements Serializable {

                private String randpic;
                private String code;
                private int mo_type;
                private int type;
                private String is_publish;
                private String randpic_iphone6;
                private String randpic_desc;

                public String getRandpic() {
                    return randpic;
                }

//                public void setRandpic(String randpic) {
//                    this.randpic = randpic;
//                }

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

//                public int getMo_type() {
//                    return mo_type;
//                }
//
//                public void setMo_type(int mo_type) {
//                    this.mo_type = mo_type;
//                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

//                public String getIs_publish() {
//                    return is_publish;
//                }
//
//                public void setIs_publish(String is_publish) {
//                    this.is_publish = is_publish;
//                }
//
//                public String getRandpic_iphone6() {
//                    return randpic_iphone6;
//                }
//
//                public void setRandpic_iphone6(String randpic_iphone6) {
//                    this.randpic_iphone6 = randpic_iphone6;
//                }
//
//                public String getRandpic_desc() {
//                    return randpic_desc;
//                }
//
//                public void setRandpic_desc(String randpic_desc) {
//                    this.randpic_desc = randpic_desc;
//                }
            }
        }

        public static class Mix2Bean implements Serializable {

            int error_code;

            public List<Mix2BeanResultBeanXX> getResult() {
                return result;
            }

            List<Mix2BeanResultBeanXX> result;


            @Override
            public String toString() {
                return "Mix2Bean{" +
                        "error_code=" + error_code +
                        ", result=" + result +
                        '}';
            }

            public static class Mix2BeanResultBeanXX implements Serializable {

                private String desc;
                private String pic;
                private String type_id;
                private int type;
                private String title;
                private int tip_type;
                private String author;

                @Override
                public String toString() {
                    return "ResultBeanXX{" +
                            "desc='" + desc + '\'' +
                            ", pic='" + pic + '\'' +
                            ", type_id='" + type_id + '\'' +
                            ", type=" + type +
                            ", title='" + title + '\'' +
                            ", tip_type=" + tip_type +
                            ", author='" + author + '\'' +
                            '}';
                }

                public String getDesc() {
                    return desc;
                }

//                public void setDesc(String desc) {
//                    this.desc = desc;
//                }

                public String getPic() {
                    return pic;
                }

//                public void setPic(String pic) {
//                    this.pic = pic;
//                }
//
//                public String getType_id() {
//                    return type_id;
//                }
//
//                public void setType_id(String type_id) {
//                    this.type_id = type_id;
//                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

//                public int getTip_type() {
//                    return tip_type;
//                }
//
//                public void setTip_type(int tip_type) {
//                    this.tip_type = tip_type;
//                }
//
//                public String getAuthor() {
//                    return author;
//                }
//
//                public void setAuthor(String author) {
//                    this.author = author;
//                }
            }
        }


        public static class Mix22Bean implements Serializable {

            private int error_code;
            private List<Mix22BeanResultBeanXX> result;

            public List<Mix22BeanResultBeanXX> getResult() {
                return result;
            }

            @Override
            public String toString() {
                return "Mix22Bean{" +
                        "error_code=" + error_code +
                        ", result=" + result +
                        '}';
            }

            public int getError_code() {
                return error_code;
            }

            public void setError_code(int error_code) {
                this.error_code = error_code;
            }

            public void setResult(List<Mix22BeanResultBeanXX> result) {
                this.result = result;
            }

            public static class Mix22BeanResultBeanXX implements Serializable {

                private String desc;
                private String pic;
                private String type_id;
                private int type;
                private String title;
                private int tip_type;
                private String author;

                public void setDesc(String desc) {
                    this.desc = desc;
                }

                public void setPic(String pic) {
                    this.pic = pic;
                }

                public void setType_id(String type_id) {
                    this.type_id = type_id;
                }

                public void setTip_type(int tip_type) {
                    this.tip_type = tip_type;
                }

                public void setAuthor(String author) {
                    this.author = author;
                }

                public String getDesc() {
                    return desc;
                }

                public String getType_id() {
                    return type_id;
                }

                public int getTip_type() {
                    return tip_type;
                }

                public String getPic() {
                    return pic;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getAuthor() {
                    return author;
                }

                @Override
                public String toString() {
                    return "Mix22BeanResultBeanXX{" +
                            "desc='" + desc + '\'' +
                            ", pic='" + pic + '\'' +
                            ", type_id='" + type_id + '\'' +
                            ", type=" + type +
                            ", title='" + title + '\'' +
                            ", tip_type=" + tip_type +
                            ", author='" + author + '\'' +
                            '}';
                }
            }
        }

        public static class ShowListBean implements Serializable {
            /**
             * error_code : 22000
             * result : [{"type":"learn","picture_iphone6":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_73fd36cf0747dbe57d614e1bb9619941.jpg","picture":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_6dd2a7b10ce1ec57eeb6ab08e48f227d.jpg","web_url":"http://music.baidu.com/cms/webview/ktv_activity/20170220/index.html"},{"type":"learn","picture_iphone6":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_016beec46a0dcd0e5f13b833fb48d561.jpg","picture":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_0c1de026bdcde37137344a5b92e73b92.jpg","web_url":"http://music.baidu.com/cms/webview/ktv_activity/20170120/index.html"},{"type":"learn","picture_iphone6":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_2bc81ed7070a7db830a9c8309080d2f4.jpg","picture":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_2b983ddd40a431e1b66a6cb290f0a770.jpg","web_url":"http://music.baidu.com/cms/webview/ktv_activity/20170112/"}]
             */

            private int error_code;
            private List<ResultBeanXXX> result;

            public List<ResultBeanXXX> getResult() {
                return result;
            }

            public static class ResultBeanXXX implements Serializable {
                /**
                 * type : learn
                 * picture_iphone6 : http://business.cdn.qianqian.com/qianqian/pic/bos_client_73fd36cf0747dbe57d614e1bb9619941.jpg
                 * picture : http://business.cdn.qianqian.com/qianqian/pic/bos_client_6dd2a7b10ce1ec57eeb6ab08e48f227d.jpg
                 * web_url : http://music.baidu.com/cms/webview/ktv_activity/20170220/index.html
                 */

                private String type;
                private String picture_iphone6;
                private String picture;
                private String web_url;

                public String getPicture_iphone6() {
                    return picture_iphone6;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }


                public void setPicture_iphone6(String picture_iphone6) {
                    this.picture_iphone6 = picture_iphone6;
                }

                public String getPicture() {
                    return picture;
                }

                public void setPicture(String picture) {
                    this.picture = picture;
                }

                public String getWeb_url() {
                    return web_url;
                }

                public void setWeb_url(String web_url) {
                    this.web_url = web_url;
                }
            }
        }

        public static class EntryBean implements Serializable {
            /**
             * error_code : 22000
             * result : [{"day":"","title":"歌手","icon":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_14639875926652ed7c4988517cab87526f15d8f359.jpg","jump":"2"},{"day":"","title":"歌曲分类","icon":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_146398764316d87d01865b91f90a598777b1569fdf.jpg","jump":"1"},{"day":"03","title":"今日推荐歌曲","icon":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_1463987629793f4361391282bde14d9b19156cfac3.jpg","jump":"0"}]
             */

            private int error_code;
            private List<ResultBeanXXXX> result;

            public List<ResultBeanXXXX> getResult() {
                return result;
            }

            public static class ResultBeanXXXX implements Serializable {
                /**
                 * day :
                 * title : 歌手
                 * icon : http://business.cdn.qianqian.com/qianqian/pic/bos_client_14639875926652ed7c4988517cab87526f15d8f359.jpg
                 * jump : 2
                 */

                private String day;
                private String title;
                private String icon;
                private String jump;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getIcon() {
                    return icon;
                }

                public void setIcon(String icon) {
                    this.icon = icon;
                }
            }
        }

        public static class SceneBean implements Serializable {

            private ResultBeanXXXXX result;
            private int error_code;
            private List<ConfigBean> config;

            public ResultBeanXXXXX getResult() {
                return result;
            }

            public List<ConfigBean> getConfig() {
                return config;
            }

            public static class ResultBeanXXXXX implements Serializable {
                private List<ActionBean> action;
                private List<EmotionBean> emotion;
                private List<OperationBean> operation;
                private List<OtherBean> other;

                public List<ActionBean> getAction() {
                    return action;
                }

                public void setAction(List<ActionBean> action) {
                    this.action = action;
                }

                public List<EmotionBean> getEmotion() {
                    return emotion;
                }

                public void setEmotion(List<EmotionBean> emotion) {
                    this.emotion = emotion;
                }

                public List<OperationBean> getOperation() {
                    return operation;
                }

                public void setOperation(List<OperationBean> operation) {
                    this.operation = operation;
                }

                public List<OtherBean> getOther() {
                    return other;
                }

                public void setOther(List<OtherBean> other) {
                    this.other = other;
                }

                public static class ActionBean implements Serializable {
                    /**
                     * icon_ios : http://b.hiphotos.baidu.com/ting/pic/item/94cad1c8a786c917cd5a64c9cf3d70cf3ac757e0.jpg
                     * scene_name : 在路上
                     * bgpic_android :
                     * icon_android : http://c.hiphotos.baidu.com/ting/pic/item/b999a9014c086e06604a914805087bf40bd1cbd7.jpg
                     * scene_model : 2
                     * scene_desc :
                     * bgpic_ios :
                     * scene_id : 0
                     */

                    private String icon_ios;
                    private String scene_name;
                    private String bgpic_android;
                    private String icon_android;
                    private String scene_model;
                    private String scene_desc;
                    private String bgpic_ios;
                    private String scene_id;

                    public String getIcon_ios() {
                        return icon_ios;
                    }

                    public void setIcon_ios(String icon_ios) {
                        this.icon_ios = icon_ios;
                    }

                    public String getScene_name() {
                        return scene_name;
                    }

                    public void setScene_name(String scene_name) {
                        this.scene_name = scene_name;
                    }

                    public String getBgpic_android() {
                        return bgpic_android;
                    }

                    public void setBgpic_android(String bgpic_android) {
                        this.bgpic_android = bgpic_android;
                    }

                    public String getIcon_android() {
                        return icon_android;
                    }

                    public void setIcon_android(String icon_android) {
                        this.icon_android = icon_android;
                    }

                    public String getScene_model() {
                        return scene_model;
                    }

                    public void setScene_model(String scene_model) {
                        this.scene_model = scene_model;
                    }

                    public String getScene_desc() {
                        return scene_desc;
                    }

                    public void setScene_desc(String scene_desc) {
                        this.scene_desc = scene_desc;
                    }

                    public String getBgpic_ios() {
                        return bgpic_ios;
                    }

                    public void setBgpic_ios(String bgpic_ios) {
                        this.bgpic_ios = bgpic_ios;
                    }

                    public String getScene_id() {
                        return scene_id;
                    }

                    public void setScene_id(String scene_id) {
                        this.scene_id = scene_id;
                    }
                }

                public static class EmotionBean implements Serializable {
                    /**
                     * icon_ios : http://d.hiphotos.baidu.com/ting/pic/item/7acb0a46f21fbe094353e1e46d600c338744ad34.jpg
                     * scene_name : 轻松
                     * bgpic_android :
                     * icon_android : http://c.hiphotos.baidu.com/ting/pic/item/bf096b63f6246b609bc02d77ecf81a4c500fa2e3.jpg
                     * scene_model : 1
                     * scene_desc :
                     * bgpic_ios :
                     * scene_id : 40
                     */

                    private String icon_ios;
                    private String scene_name;
                    private String bgpic_android;
                    private String icon_android;
                    private String scene_model;
                    private String scene_desc;
                    private String bgpic_ios;
                    private String scene_id;

                    public String getIcon_ios() {
                        return icon_ios;
                    }

                    public void setIcon_ios(String icon_ios) {
                        this.icon_ios = icon_ios;
                    }

                    public String getScene_name() {
                        return scene_name;
                    }

                    public void setScene_name(String scene_name) {
                        this.scene_name = scene_name;
                    }

                    public String getBgpic_android() {
                        return bgpic_android;
                    }

                    public void setBgpic_android(String bgpic_android) {
                        this.bgpic_android = bgpic_android;
                    }

                    public String getIcon_android() {
                        return icon_android;
                    }

                    public void setIcon_android(String icon_android) {
                        this.icon_android = icon_android;
                    }

                    public String getScene_model() {
                        return scene_model;
                    }

                    public void setScene_model(String scene_model) {
                        this.scene_model = scene_model;
                    }

                    public String getScene_desc() {
                        return scene_desc;
                    }

                    public void setScene_desc(String scene_desc) {
                        this.scene_desc = scene_desc;
                    }

                    public String getBgpic_ios() {
                        return bgpic_ios;
                    }

                    public void setBgpic_ios(String bgpic_ios) {
                        this.bgpic_ios = bgpic_ios;
                    }

                    public String getScene_id() {
                        return scene_id;
                    }

                    public void setScene_id(String scene_id) {
                        this.scene_id = scene_id;
                    }
                }

                public static class OperationBean implements Serializable {
                    /**
                     * icon_ios : http://d.hiphotos.baidu.com/ting/pic/item/cb8065380cd79123bb1999f9ab345982b3b78045.jpg
                     * scene_name : 小清新
                     * bgpic_android :
                     * icon_android : http://a.hiphotos.baidu.com/ting/pic/item/80cb39dbb6fd526672003a16ac18972bd407368c.jpg
                     * scene_model : 1
                     * scene_desc :
                     * bgpic_ios :
                     * scene_id : 158
                     */

                    private String icon_ios;
                    private String scene_name;
                    private String bgpic_android;
                    private String icon_android;
                    private String scene_model;
                    private String scene_desc;
                    private String bgpic_ios;
                    private String scene_id;

                    public String getIcon_ios() {
                        return icon_ios;
                    }

                    public void setIcon_ios(String icon_ios) {
                        this.icon_ios = icon_ios;
                    }

                    public String getScene_name() {
                        return scene_name;
                    }

                    public void setScene_name(String scene_name) {
                        this.scene_name = scene_name;
                    }

                    public String getBgpic_android() {
                        return bgpic_android;
                    }

                    public void setBgpic_android(String bgpic_android) {
                        this.bgpic_android = bgpic_android;
                    }

                    public String getIcon_android() {
                        return icon_android;
                    }

                    public void setIcon_android(String icon_android) {
                        this.icon_android = icon_android;
                    }

                    public String getScene_model() {
                        return scene_model;
                    }

                    public void setScene_model(String scene_model) {
                        this.scene_model = scene_model;
                    }

                    public String getScene_desc() {
                        return scene_desc;
                    }

                    public void setScene_desc(String scene_desc) {
                        this.scene_desc = scene_desc;
                    }

                    public String getBgpic_ios() {
                        return bgpic_ios;
                    }

                    public void setBgpic_ios(String bgpic_ios) {
                        this.bgpic_ios = bgpic_ios;
                    }

                    public String getScene_id() {
                        return scene_id;
                    }

                    public void setScene_id(String scene_id) {
                        this.scene_id = scene_id;
                    }
                }

                public static class OtherBean implements Serializable {
                    /**
                     * icon_ios : http://d.hiphotos.baidu.com/ting/pic/item/377adab44aed2e7364d7a8dc8101a18b87d6fa00.jpg
                     * scene_name : 2000年代
                     * bgpic_android :
                     * icon_android : http://b.hiphotos.baidu.com/ting/pic/item/aa64034f78f0f736ec9dd4020d55b319ebc41355.jpg
                     * scene_model : 1
                     * scene_desc :
                     * bgpic_ios :
                     * scene_id : 71
                     */

                    private String icon_ios;
                    private String scene_name;
                    private String bgpic_android;
                    private String icon_android;
                    private String scene_model;
                    private String scene_desc;
                    private String bgpic_ios;
                    private String scene_id;

                    public String getIcon_ios() {
                        return icon_ios;
                    }

                    public void setIcon_ios(String icon_ios) {
                        this.icon_ios = icon_ios;
                    }

                    public String getScene_name() {
                        return scene_name;
                    }

                    public void setScene_name(String scene_name) {
                        this.scene_name = scene_name;
                    }

                    public String getBgpic_android() {
                        return bgpic_android;
                    }

                    public void setBgpic_android(String bgpic_android) {
                        this.bgpic_android = bgpic_android;
                    }

                    public String getIcon_android() {
                        return icon_android;
                    }

                    public void setIcon_android(String icon_android) {
                        this.icon_android = icon_android;
                    }

                    public String getScene_model() {
                        return scene_model;
                    }

                    public void setScene_model(String scene_model) {
                        this.scene_model = scene_model;
                    }

                    public String getScene_desc() {
                        return scene_desc;
                    }

                    public void setScene_desc(String scene_desc) {
                        this.scene_desc = scene_desc;
                    }

                    public String getBgpic_ios() {
                        return bgpic_ios;
                    }

                    public void setBgpic_ios(String bgpic_ios) {
                        this.bgpic_ios = bgpic_ios;
                    }

                    public String getScene_id() {
                        return scene_id;
                    }

                    public void setScene_id(String scene_id) {
                        this.scene_id = scene_id;
                    }
                }
            }

            public static class ConfigBean implements Serializable {
                /**
                 * color_other :
                 * play_color :
                 * scene_version : 0
                 * desc :
                 * end_time : 0
                 * start_time : 0
                 * scene_color :
                 * bgpic :
                 * bgpic_special :
                 * button_color :
                 */

                private String color_other;
                private String play_color;
                private int scene_version;
                private String desc;
                private int end_time;
                private int start_time;
                private String scene_color;
                private String bgpic;
                private String bgpic_special;
                private String button_color;

                public String getColor_other() {
                    return color_other;
                }

                public void setColor_other(String color_other) {
                    this.color_other = color_other;
                }

                public String getPlay_color() {
                    return play_color;
                }

                public void setPlay_color(String play_color) {
                    this.play_color = play_color;
                }

                public int getScene_version() {
                    return scene_version;
                }

                public void setScene_version(int scene_version) {
                    this.scene_version = scene_version;
                }

                public String getDesc() {
                    return desc;
                }

                public void setDesc(String desc) {
                    this.desc = desc;
                }

                public int getEnd_time() {
                    return end_time;
                }

                public void setEnd_time(int end_time) {
                    this.end_time = end_time;
                }

                public int getStart_time() {
                    return start_time;
                }

                public void setStart_time(int start_time) {
                    this.start_time = start_time;
                }

                public String getScene_color() {
                    return scene_color;
                }

                public void setScene_color(String scene_color) {
                    this.scene_color = scene_color;
                }

                public String getBgpic() {
                    return bgpic;
                }

                public void setBgpic(String bgpic) {
                    this.bgpic = bgpic;
                }

                public String getBgpic_special() {
                    return bgpic_special;
                }

                public void setBgpic_special(String bgpic_special) {
                    this.bgpic_special = bgpic_special;
                }

                public String getButton_color() {
                    return button_color;
                }

                public void setButton_color(String button_color) {
                    this.button_color = button_color;
                }
            }
        }


        public static class Mix1Bean implements Serializable {
            /**
             * error_code : 22000
             * result : [{"desc":"Lorde","pic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_1488527156473977b29c7249520a7f196ff8c7a595.jpg","type_id":"325272266","type":2,"title":"Green Light","tip_type":0,"author":"Lorde"},{"desc":"Alan Walker","pic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_148851786790d9a3fcffb5f7602f394a016daf8fbe.jpg","type_id":"536788372","type":2,"title":"Alone (Remixes)","tip_type":0,"author":"Alan Walker"},{"desc":"By2","pic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_148850856365e5894d1b6cf90f7b9c657aa442f0da.jpg","type_id":"533370111","type":2,"title":"爱又爱","tip_type":3,"author":"By2"},{"desc":"刘力扬","pic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_148850658951eed7991aebf73b57c7d829d042fbcb.jpg","type_id":"535079548","type":2,"title":"Warriors (战士)","tip_type":0,"author":"刘力扬"},{"desc":"代岳东","pic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_14885065515211b263ae7186018270c886d0ee6c46.jpg","type_id":"536041177","type":2,"title":"单身日记","tip_type":0,"author":"代岳东"},{"desc":"齐硕","pic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_1488452900ddfbc9d3d8fddc4360d694e8b6345d17.jpg","type_id":"533367288","type":2,"title":"我的室友是狐仙之青春未央 网剧原声带","tip_type":0,"author":"齐硕"}]
             */

            private int error_code;
            private List<ResultBeanXXXXXXX> result;

            public int getError_code() {
                return error_code;
            }

            public void setError_code(int error_code) {
                this.error_code = error_code;
            }

            public List<ResultBeanXXXXXXX> getResult() {
                return result;
            }

            public void setResult(List<ResultBeanXXXXXXX> result) {
                this.result = result;
            }

            public static class ResultBeanXXXXXXX implements Serializable {
                /**
                 * desc : Lorde
                 * pic : http://business.cdn.qianqian.com/qianqian/pic/bos_client_1488527156473977b29c7249520a7f196ff8c7a595.jpg
                 * type_id : 325272266
                 * type : 2
                 * title : Green Light
                 * tip_type : 0
                 * author : Lorde
                 */

                private String desc;
                private String pic;
                private String type_id;
                private int type;
                private String title;
                private int tip_type;
                private String author;

                public String getDesc() {
                    return desc;
                }

                public void setDesc(String desc) {
                    this.desc = desc;
                }

                public String getPic() {
                    return pic;
                }

                public void setPic(String pic) {
                    this.pic = pic;
                }

                public String getType_id() {
                    return type_id;
                }

                public void setType_id(String type_id) {
                    this.type_id = type_id;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public int getTip_type() {
                    return tip_type;
                }

                public void setTip_type(int tip_type) {
                    this.tip_type = tip_type;
                }

                public String getAuthor() {
                    return author;
                }

                public void setAuthor(String author) {
                    this.author = author;
                }
            }
        }

        public static class RecsongBean implements Serializable {

            private int error_code;
            private List<ResultBeanXXXXXXXX> result;

            public int getError_code() {
                return error_code;
            }

            public void setError_code(int error_code) {
                this.error_code = error_code;
            }

            public List<ResultBeanXXXXXXXX> getResult() {
                return result;
            }

            public void setResult(List<ResultBeanXXXXXXXX> result) {
                this.result = result;
            }

            public static class ResultBeanXXXXXXXX implements Serializable {
                /**
                 * resource_type_ext : 0
                 * learn : 0
                 * del_status : 0
                 * korean_bb_song : 0
                 * versions :
                 * title : 亲爱的 你在哪里
                 * bitrate_fee : {"0":"0|0","1":"0|0"}
                 * song_id : 130259272
                 * has_mv_mobile : 0
                 * pic_premium : http://musicdata.baidu.com/data2/pic/130255668/130255668.jpg@s_0,w_500
                 * author : 龙飞,门丽
                 */

                private String resource_type_ext;
                private String learn;
                private String del_status;
                private String korean_bb_song;
                private String versions;
                private String title;
                private String bitrate_fee;
                private String song_id;
                private String has_mv_mobile;
                private String pic_premium;
                private String author;

                public String getResource_type_ext() {
                    return resource_type_ext;
                }

                public void setResource_type_ext(String resource_type_ext) {
                    this.resource_type_ext = resource_type_ext;
                }

                public String getLearn() {
                    return learn;
                }

                public void setLearn(String learn) {
                    this.learn = learn;
                }

                public String getDel_status() {
                    return del_status;
                }

                public void setDel_status(String del_status) {
                    this.del_status = del_status;
                }

                public String getKorean_bb_song() {
                    return korean_bb_song;
                }

                public void setKorean_bb_song(String korean_bb_song) {
                    this.korean_bb_song = korean_bb_song;
                }

                public String getVersions() {
                    return versions;
                }

                public void setVersions(String versions) {
                    this.versions = versions;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getBitrate_fee() {
                    return bitrate_fee;
                }

                public void setBitrate_fee(String bitrate_fee) {
                    this.bitrate_fee = bitrate_fee;
                }

                public String getSong_id() {
                    return song_id;
                }

                public void setSong_id(String song_id) {
                    this.song_id = song_id;
                }

                public String getHas_mv_mobile() {
                    return has_mv_mobile;
                }

                public void setHas_mv_mobile(String has_mv_mobile) {
                    this.has_mv_mobile = has_mv_mobile;
                }

                public String getPic_premium() {
                    return pic_premium;
                }

                public void setPic_premium(String pic_premium) {
                    this.pic_premium = pic_premium;
                }

                public String getAuthor() {
                    return author;
                }

                public void setAuthor(String author) {
                    this.author = author;
                }
            }
        }

        public static class RadioBean implements Serializable {
            /**
             * error_code : 22000
             * result : [{"desc":"都市情感","itemid":"13429922","title":"《当我知道你们相爱》：每一场青春注定散场（主播：辰熙澤）","album_id":"12545344","type":"lebo","channelid":"11373553","pic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_5cdb36efab04981e957bfb61680403af.jpg"},{"desc":"综艺娱乐","itemid":"13428897","title":"Vol.199 \u201c正确\u201d的第89届奥斯卡","album_id":"1088420","type":"lebo","channelid":"11373550","pic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_ab1a9e18c265459120a96a432be22ad8.jpg"},{"desc":"都市情感","itemid":"13429504","title":"【墨色声弦】一曲旧梦终难醒-NJ胤烟","album_id":"5568804","type":"lebo","channelid":"11373553","pic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_6cbdc41eaf9e819019b4f464fda8e6aa.jpg"},{"desc":"都市情感","itemid":"13426131","title":"《一个像夏天一个像秋天》 ：\u201c女\u201d朋友（主播：金心人）","album_id":"12545344","type":"lebo","channelid":"11373553","pic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_27c43d1341a905e52165736be08994d2.jpg"},{"desc":"脱口秀","itemid":"13425511","title":"三好学堂 \u2013 紫微斗数贴身教学","album_id":"1168147","type":"lebo","channelid":"11373548","pic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_235b9aed64dc5826f862d5877a1c2e54.jpg"},{"desc":"笑话段子","itemid":"13426916","title":"人可以穷到什么地步？","album_id":"11448439","type":"lebo","channelid":"11373547","pic":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_62cdc9aa6457202323cbec73fcc2ec00.jpg"}]
             */

            private int error_code;
            private List<ResultBeanXXXXXXXXX> result;

            public int getError_code() {
                return error_code;
            }

            public void setError_code(int error_code) {
                this.error_code = error_code;
            }

            public List<ResultBeanXXXXXXXXX> getResult() {
                return result;
            }

            public void setResult(List<ResultBeanXXXXXXXXX> result) {
                this.result = result;
            }

            public static class ResultBeanXXXXXXXXX implements Serializable {
                /**
                 * desc : 都市情感
                 * itemid : 13429922
                 * title : 《当我知道你们相爱》：每一场青春注定散场（主播：辰熙澤）
                 * album_id : 12545344
                 * type : lebo
                 * channelid : 11373553
                 * pic : http://business.cdn.qianqian.com/qianqian/pic/bos_client_5cdb36efab04981e957bfb61680403af.jpg
                 */

                private String desc;
                private String itemid;
                private String title;
                private String album_id;
                private String type;
                private String channelid;
                private String pic;

                public String getDesc() {
                    return desc;
                }

                public void setDesc(String desc) {
                    this.desc = desc;
                }

                public String getItemid() {
                    return itemid;
                }

                public void setItemid(String itemid) {
                    this.itemid = itemid;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getAlbum_id() {
                    return album_id;
                }

                public void setAlbum_id(String album_id) {
                    this.album_id = album_id;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getChannelid() {
                    return channelid;
                }

                public void setChannelid(String channelid) {
                    this.channelid = channelid;
                }

                public String getPic() {
                    return pic;
                }

                public void setPic(String pic) {
                    this.pic = pic;
                }
            }
        }

        public static class NewSongBean implements Serializable {
            /**
             * error_code : 22000
             * result : {"pic_500":"http://b.hiphotos.baidu.com/ting/pic/item/a50f4bfbfbedab64bcae572ef136afc378311e7b.jpg","listid":"5126","song_info":[{"song_id":"261812117","title":"二十四小时","pic_premium":"http://qukufile2.qianqian.com/data2/pic/261811991/261811991.jpg@s_0,w_500","author":"陈坤,韩庚,大鹏,吴磊,尹正"},{"song_id":"74109283","title":"灵主不悔《画江湖之灵主》手游暨动漫主题曲","author":"汪苏泷"},{"song_id":"261496612","title":"Protocole","pic_premium":"http://qukufile2.qianqian.com/data2/pic/07a830e962bbb4e58e29842f45d44b66/261496583/261496583.jpg@s_0,w_500","author":"Alpha Wann"}]}
             */

            private int error_code;
            private ResultBeanXXXXXXXXXX result;

            public int getError_code() {
                return error_code;
            }

            public void setError_code(int error_code) {
                this.error_code = error_code;
            }

            public ResultBeanXXXXXXXXXX getResult() {
                return result;
            }

            public void setResult(ResultBeanXXXXXXXXXX result) {
                this.result = result;
            }

            public static class ResultBeanXXXXXXXXXX implements Serializable {
                /**
                 * pic_500 : http://b.hiphotos.baidu.com/ting/pic/item/a50f4bfbfbedab64bcae572ef136afc378311e7b.jpg
                 * listid : 5126
                 * song_info : [{"song_id":"261812117","title":"二十四小时","pic_premium":"http://qukufile2.qianqian.com/data2/pic/261811991/261811991.jpg@s_0,w_500","author":"陈坤,韩庚,大鹏,吴磊,尹正"},{"song_id":"74109283","title":"灵主不悔《画江湖之灵主》手游暨动漫主题曲","author":"汪苏泷"},{"song_id":"261496612","title":"Protocole","pic_premium":"http://qukufile2.qianqian.com/data2/pic/07a830e962bbb4e58e29842f45d44b66/261496583/261496583.jpg@s_0,w_500","author":"Alpha Wann"}]
                 */

                private String pic_500;
                private String listid;
                private List<SongInfoBean> song_info;

                public String getPic_500() {
                    return pic_500;
                }

                public void setPic_500(String pic_500) {
                    this.pic_500 = pic_500;
                }

                public String getListid() {
                    return listid;
                }

                public void setListid(String listid) {
                    this.listid = listid;
                }

                public List<SongInfoBean> getSong_info() {
                    return song_info;
                }

                public void setSong_info(List<SongInfoBean> song_info) {
                    this.song_info = song_info;
                }

                public static class SongInfoBean implements Serializable {
                    /**
                     * song_id : 261812117
                     * title : 二十四小时
                     * pic_premium : http://qukufile2.qianqian.com/data2/pic/261811991/261811991.jpg@s_0,w_500
                     * author : 陈坤,韩庚,大鹏,吴磊,尹正
                     */

                    private String song_id;
                    private String title;
                    private String pic_premium;
                    private String author;

                    public String getSong_id() {
                        return song_id;
                    }

                    public void setSong_id(String song_id) {
                        this.song_id = song_id;
                    }

                    public String getTitle() {
                        return title;
                    }

                    public void setTitle(String title) {
                        this.title = title;
                    }

                    public String getPic_premium() {
                        return pic_premium;
                    }

                    public void setPic_premium(String pic_premium) {
                        this.pic_premium = pic_premium;
                    }

                    public String getAuthor() {
                        return author;
                    }

                    public void setAuthor(String author) {
                        this.author = author;
                    }
                }
            }
        }

        public static class DiyBean implements Serializable {
            /**
             * error_code : 22000
             * result : [{"position":1,"tag":"华语,流行,伤感","songidlist":[],"pic":"http://musicugc.cdn.qianqian.com/ugcdiy/pic/066e1da7df36504586d539fe6019c661.jpg","title":"【环球之音】直戳泪点的伤感情歌","collectnum":34,"type":"gedan","listenum":14259,"listid":"365146546"},{"position":2,"tag":"日语,流行,美好","songidlist":[],"pic":"http://musicugc.cdn.qianqian.com/ugcdiy/pic/2552050c1c219484b76fd7b234fd663d.jpg","title":"日系温柔，触到心底的旋律","collectnum":20,"type":"gedan","listenum":1893,"listid":"365141303"},{"position":3,"tag":"粤语,流行,午后","songidlist":[],"pic":"http://musicugc.cdn.qianqian.com/ugcdiy/pic/9d4333977c8b92c9a43d5915f09ff89c.jpg","title":"因为一个人，而听懂一首歌","collectnum":9,"type":"gedan","listenum":3847,"listid":"365143172"},{"position":4,"tag":"欧美,怀旧,经典","songidlist":[],"pic":"http://musicugc.cdn.qianqian.com/ugcdiy/pic/bb17e3faa9fc5c0eb477ce28a035a0d6.jpg","title":"【环球之音】Island岛屿音乐时光","collectnum":91,"type":"gedan","listenum":20658,"listid":"364522605"},{"position":5,"tag":"雷鬼,放松","songidlist":[],"pic":"http://musicugc.cdn.qianqian.com/ugcdiy/pic/da07dcec3fc463bcdbc118595bfb08c4.jpg","title":"听雷鬼，一起啦啦啦","collectnum":4,"type":"gedan","listenum":816,"listid":"365142492"},{"position":6,"tag":"华语,流行,安静","songidlist":[],"pic":"http://musicugc.cdn.qianqian.com/ugcdiy/pic/bf19b6c2d7d04606961d2eb4738d6db1.jpg","title":"华语 · 滚滚红尘，似水柔情","collectnum":62,"type":"gedan","listenum":28499,"listid":"364085981"}]
             */

            private int error_code;
            private List<ResultBeanXXXXXXXXXXX> result;

            public int getError_code() {
                return error_code;
            }

            public void setError_code(int error_code) {
                this.error_code = error_code;
            }

            public List<ResultBeanXXXXXXXXXXX> getResult() {
                return result;
            }

            public void setResult(List<ResultBeanXXXXXXXXXXX> result) {
                this.result = result;
            }

            public static class ResultBeanXXXXXXXXXXX implements Serializable{
                /**
                 * position : 1
                 * tag : 华语,流行,伤感
                 * songidlist : []
                 * pic : http://musicugc.cdn.qianqian.com/ugcdiy/pic/066e1da7df36504586d539fe6019c661.jpg
                 * title : 【环球之音】直戳泪点的伤感情歌
                 * collectnum : 34
                 * type : gedan
                 * listenum : 14259
                 * listid : 365146546
                 */

                private int position;
                private String tag;
                private String pic;
                private String title;
                private int collectnum;
                private String type;
                private int listenum;
                private String listid;
                private List<?> songidlist;

                public int getPosition() {
                    return position;
                }

                public void setPosition(int position) {
                    this.position = position;
                }

                public String getTag() {
                    return tag;
                }

                public void setTag(String tag) {
                    this.tag = tag;
                }

                public String getPic() {
                    return pic;
                }

                public void setPic(String pic) {
                    this.pic = pic;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public int getCollectnum() {
                    return collectnum;
                }

                public void setCollectnum(int collectnum) {
                    this.collectnum = collectnum;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public int getListenum() {
                    return listenum;
                }

                public void setListenum(int listenum) {
                    this.listenum = listenum;
                }

                public String getListid() {
                    return listid;
                }

                public void setListid(String listid) {
                    this.listid = listid;
                }

                public List<?> getSongidlist() {
                    return songidlist;
                }

                public void setSongidlist(List<?> songidlist) {
                    this.songidlist = songidlist;
                }
            }
        }


        public static class KingBean implements Serializable {
            /**
             * error_code : 22000
             * result : [{"pic_big":"http://musicdata.baidu.com/data2/pic/c1575cf222e45e809bcd2cd9edc7ac4b/533531419/533531419.jpg@s_0,w_150","title":"可疑","author":"厉娜"},{"pic_big":"http://musicdata.baidu.com/data2/pic/6f0360642b613a192e2e9b459ab76341/533553518/533553518.jpg@s_0,w_150","title":"恋をする (恋爱)","author":"中岛美嘉"},{"pic_big":"http://musicdata.baidu.com/data2/pic/dee57a075da12ee283c6e9ba9dbf9b66/531451362/531451362.jpg@s_0,w_150","title":" Goodbye","author":"2NE1"}]
             */

            private int error_code;
            private List<ResultBeanXXXXXXXXXXXXX> result;

            public int getError_code() {
                return error_code;
            }

            public void setError_code(int error_code) {
                this.error_code = error_code;
            }

            public List<ResultBeanXXXXXXXXXXXXX> getResult() {
                return result;
            }

            public void setResult(List<ResultBeanXXXXXXXXXXXXX> result) {
                this.result = result;
            }

            public static class ResultBeanXXXXXXXXXXXXX implements Serializable{
                /**
                 * pic_big : http://musicdata.baidu.com/data2/pic/c1575cf222e45e809bcd2cd9edc7ac4b/533531419/533531419.jpg@s_0,w_150
                 * title : 可疑
                 * author : 厉娜
                 */

                private String pic_big;
                private String title;
                private String author;

                public String getPic_big() {
                    return pic_big;
                }

                public void setPic_big(String pic_big) {
                    this.pic_big = pic_big;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getAuthor() {
                    return author;
                }

                public void setAuthor(String author) {
                    this.author = author;
                }
            }
        }
    }

    public static class ModuleBean implements Serializable {
        /**
         * link_url :
         * pos : 1
         * title : 焦点图
         * key : focus
         * picurl :
         * title_more :
         * style : 1
         * jump :
         */

        private String id;
        private String style_nums;

        private String link_url;
        private int pos;
        private String title;
        private String key;
        private String picurl;
        private String title_more;
        private int style;
        private int nums;
        private String jump;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStyle_nums() {
            return style_nums;
        }

        public void setStyle_nums(String style_nums) {
            this.style_nums = style_nums;
        }

        public int getNums() {
            return nums;
        }

        public void setNums(int nums) {
            this.nums = nums;
        }

        public String getLink_url() {
            return link_url;
        }

        public void setLink_url(String link_url) {
            this.link_url = link_url;
        }

        public int getPos() {
            return pos;
        }

        public void setPos(int pos) {
            this.pos = pos;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getPicurl() {
            return picurl;
        }

        public void setPicurl(String picurl) {
            this.picurl = picurl;
        }

        public String getTitle_more() {
            return title_more;
        }

        public void setTitle_more(String title_more) {
            this.title_more = title_more;
        }

        public int getStyle() {
            return style;
        }

        public void setStyle(int style) {
            this.style = style;
        }

        public String getJump() {
            return jump;
        }

        public void setJump(String jump) {
            this.jump = jump;
        }
    }
}
