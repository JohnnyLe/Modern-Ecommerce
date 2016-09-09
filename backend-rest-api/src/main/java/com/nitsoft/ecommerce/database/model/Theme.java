package com.nitsoft.ecommerce.database.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "themes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Theme.findAll", query = "SELECT t FROM Theme t"),
    @NamedQuery(name = "Theme.findByThemeId", query = "SELECT t FROM Theme t WHERE t.themeId = :themeId"),
    @NamedQuery(name = "Theme.findByName", query = "SELECT t FROM Theme t WHERE t.name = :name"),
    @NamedQuery(name = "Theme.findByVersion", query = "SELECT t FROM Theme t WHERE t.version = :version"),
    @NamedQuery(name = "Theme.findByThumbnail", query = "SELECT t FROM Theme t WHERE t.thumbnail = :thumbnail"),
    @NamedQuery(name = "Theme.findBySourcePath", query = "SELECT t FROM Theme t WHERE t.sourcePath = :sourcePath")})
public class Theme implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "theme_id")
    private Integer themeId;
    
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    
    @Basic(optional = false)
    @Column(name = "version")
    private String version;
    
    @Basic(optional = false)
    @Column(name = "thumbnail")
    private String thumbnail;
    
    @Basic(optional = false)
    @Column(name = "source_path")
    private String sourcePath;

    public Theme() {
    }

    public Theme(Integer themeId) {
        this.themeId = themeId;
    }

    public Theme(Integer themeId, String name, String version, String thumbnail, String sourcePath) {
        this.themeId = themeId;
        this.name = name;
        this.version = version;
        this.thumbnail = thumbnail;
        this.sourcePath = sourcePath;
    }

    public Integer getThemeId() {
        return themeId;
    }

    public void setThemeId(Integer themeId) {
        this.themeId = themeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (themeId != null ? themeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Theme)) {
            return false;
        }
        Theme other = (Theme) object;
        if ((this.themeId == null && other.themeId != null) || (this.themeId != null && !this.themeId.equals(other.themeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.nitsoft.ecommerce.model.Theme[ themeId=" + themeId + " ]";
    }
    
}
