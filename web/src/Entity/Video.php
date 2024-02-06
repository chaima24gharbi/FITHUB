<?php

namespace App\Entity;

use App\Repository\VideoRepository;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: VideoRepository::class)]
class Video
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $id = null;

    #[ORM\Column(length: 255)]
    private ?string $titreVideo = null;

    #[ORM\Column(length: 255)]
    private ?string $descriptionVideo = null;

    #[ORM\Column(length: 255)]
    private ?string $fileUrlVideo = null;

    #[ORM\ManyToOne(inversedBy: 'video')]
    private ?Seance $seance = null;

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getTitreVideo(): ?string
    {
        return $this->titreVideo;
    }

    public function setTitreVideo(string $titreVideo): self
    {
        $this->titreVideo = $titreVideo;

        return $this;
    }

    public function getDescriptionVideo(): ?string
    {
        return $this->descriptionVideo;
    }

    public function setDescriptionVideo(string $descriptionVideo): self
    {
        $this->descriptionVideo = $descriptionVideo;

        return $this;
    }

    public function getFileUrlVideo(): ?string
    {
        return $this->fileUrlVideo;
    }

    public function setFileUrlVideo(string $fileUrlVideo): self
    {
        $this->fileUrlVideo = $fileUrlVideo;

        return $this;
    }

    public function getSeance(): ?Seance
    {
        return $this->seance;
    }

    public function setSeance(?Seance $seance): self
    {
        $this->seance = $seance;

        return $this;
    }
}
